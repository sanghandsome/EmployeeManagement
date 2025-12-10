package com.example.employeemanagement.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TokenPayload {
    private String jwtID;
    private String token;
    private Date expirationTime;


}
