package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.PageResponse;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.model.User;
import java.util.List;


public interface UserService {

    public UserResponse createUser(UserRequest userRequest);

    public UserResponse findById(Long id) ;

    public PageResponse<UserResponse> findAll(int page, int size,String email) ;

    public UserResponse updateUser(Long id, UserRequest userRequest) ;

    public void deleteUser(Long id) ;
}
