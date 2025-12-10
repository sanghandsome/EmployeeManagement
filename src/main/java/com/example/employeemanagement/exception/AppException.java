package com.example.employeemanagement.exception;

import com.example.employeemanagement.dto.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AppException extends RuntimeException {
    public final ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
