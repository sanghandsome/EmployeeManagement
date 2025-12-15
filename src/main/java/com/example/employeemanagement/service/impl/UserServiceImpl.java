package com.example.employeemanagement.service.impl;
import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.PageResponse;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.exception.AppException;
import com.example.employeemanagement.mapper.UserMapper;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.repository.specification.UserSpecification;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

import static com.example.employeemanagement.exception.ErrorCode.USER_EXITS;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if(userRequest.getRoleId() == null){
            userRequest.addRole(1L);
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userCreate = userMapper.toUser(userRequest);
        userRepository.save(userCreate);
        return userMapper.toUserResponse(userCreate);
    }

    @Cacheable(value = "user", key = "#id")
    @Override
    public UserResponse findById(Long id) {
        log.info("Get current user: ");
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(USER_EXITS));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<UserResponse> findAll(int page, int size, String email) {
        Pageable pageable = PageRequest.of(page-1,size);

        Specification<User> specification = Specification.allOf(UserSpecification.searchByEmail(email));

        Page<User> userPage = userRepository.findAll(specification,pageable);
        List<User> users = userPage.getContent();

        List<UserResponse> userResponseList = users.stream().map(userMapper::toUserResponse).toList();

        return PageResponse.<UserResponse>builder()
                .currentPages(pageable.getPageNumber())
                .pageSizes(pageable.getPageSize())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .data(userResponseList)
                .build();
    }

    @CachePut(value = "user", key = "#id")
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User userUpdate = userRepository.findById(id).orElseThrow(() -> new AppException(USER_EXITS));
        userMapper.updateUser(userRequest, userUpdate);
        userRepository.save(userUpdate);
        return userMapper.toUserResponse(userUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(USER_EXITS));
        user.setPerson(null);
        userRepository.delete(user);
    }
}
