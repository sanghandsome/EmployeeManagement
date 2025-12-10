package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.request.RefreshTokenRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.LoginResponse;
import com.example.employeemanagement.service.impl.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {
        LoginResponse result = authenticationService.login(loginRequest);
        Cookie cookie = new Cookie("refreshToken", result.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login Successfully")
                .data(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestHeader("Authorization") String authHeader, @CookieValue("refreshToken") String refreshToken) throws ParseException {
        String accessToken = authHeader.replace("Bearer ","");
        authenticationService.logout(accessToken,refreshToken);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Logout Successfully")
                .data(null)
                .build();
    }

    @PostMapping("refresh-token")
    ApiResponse<LoginResponse> refreshToken(@CookieValue("refreshToken") String refreshToken) throws ParseException, JOSEException {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Refresh Token Successfully")
                .data(authenticationService.refreshToken(refreshToken))
                .build();
    }
}
