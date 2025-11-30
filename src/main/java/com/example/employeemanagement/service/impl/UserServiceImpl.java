package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.mapper.UserMapper;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
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
