package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.mapper.UserMapper;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {

        User userCreate = userMapper.toUser(userRequest);
        userRepository.save(userCreate);
        return userMapper.toUserResponse(userCreate);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user does not exist"));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User userUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user does not exist"));
        userMapper.updateUser(userRequest, userUpdate);
        userRepository.save(userUpdate);
        return userMapper.toUserResponse(userUpdate);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
