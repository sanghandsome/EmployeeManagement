package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.PageResponse;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.mapper.UserMapper;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.repository.specification.UserSpecification;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userCreate = userMapper.toUser(userRequest);
        userRepository.save(userCreate);
        return userMapper.toUserResponse(userCreate);
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user does not exist"));
        return userMapper.toUserResponse(user);
    }

    @Override
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

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User userUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user does not exist"));
        userMapper.updateUser(userRequest, userUpdate);
        userRepository.save(userUpdate);
        return userMapper.toUserResponse(userUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPerson(null);
        userRepository.delete(user);
    }
}
