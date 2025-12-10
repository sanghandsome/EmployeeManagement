package com.example.employeemanagement.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse {
    private String message;
    private int status;
    private Date timestamp;
    private String path;
    private String error;
}
