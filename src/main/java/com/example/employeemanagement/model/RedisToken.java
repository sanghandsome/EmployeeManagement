package com.example.employeemanagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("RedisToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RedisToken {
    @Id
    private String jwtId;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expirationTime;
}
