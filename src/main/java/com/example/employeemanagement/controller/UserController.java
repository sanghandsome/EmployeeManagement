package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.PageResponse;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.service.UserService;
import com.example.employeemanagement.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ApiResponse<PageResponse<UserResponse>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String email
    ) {
        PageResponse<UserResponse> userResponseList = userService.findAll(page, size,email);
        return ApiResponse.<PageResponse<UserResponse>> builder()
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

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        String id = jwt.getSubject();
        UserResponse userResponse = userService.findById(Long.parseLong(id));
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

    @PatchMapping ("/me")
    public ApiResponse<UserResponse> update(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid UserRequest userRequest){
        String id = jwt.getSubject();
        UserResponse userUpdate = userService.updateUser(Long.parseLong(id), userRequest);
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
