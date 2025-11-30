package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.service.UserService;
import com.example.employeemanagement.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> findAll() {
        List<UserResponse>  userResponseList = userService.findAll();
        return ApiResponse.<List<UserResponse>> builder()
                .code(HttpStatus.OK.value())
                .message("Users retrieved successfully")
                .data(userResponseList)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> findById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        UserResponse userResponse = userService.findById(id);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User retrieved successfully")
                .data(userResponse)
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> save(@RequestBody @Valid UserRequest userRequest){
        UserResponse userCreate = userService.createUser(userRequest);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(userCreate)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable @Positive(message = "Id must be greater than 0") Long id, @RequestBody @Valid UserRequest userRequest){
        UserResponse userUpdate = userService.updateUser(id, userRequest);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(userUpdate)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("User deleted successfully")
                .data(null)
                .build();
    }
}
