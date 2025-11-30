package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.LoginResponse;
import com.example.employeemanagement.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login Successfully")
                .data(authenticationService.login(loginRequest))
                .build();
    }
}
