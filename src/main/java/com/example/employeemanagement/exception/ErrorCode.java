package com.example.employeemanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    USER_EXITS(400,"User does not exist",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401,"Unauthorized",HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(403,"Invalid Token",HttpStatus.FORBIDDEN),
    ;
    private final int code;
    private final String message;
    private final HttpStatus status;
}
