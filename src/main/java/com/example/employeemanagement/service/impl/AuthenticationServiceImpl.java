package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.TokenPayload;
import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.response.LoginResponse;
import com.example.employeemanagement.exception.AppException;
import com.example.employeemanagement.model.RedisToken;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.RedisTokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

import static com.example.employeemanagement.exception.ErrorCode.INVALID_TOKEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final RedisTokenRepository redisTokenRepository;

    public LoginResponse login(LoginRequest loginRequest) throws JOSEException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        log.info("Authentication Successful: {}", authentication.isAuthenticated());

        User user = (User) authentication.getPrincipal();
        TokenPayload refreshPayload = jwtService.generateRefreshToken(user.getId());

        redisTokenRepository.save(RedisToken.builder()
                        .jwtId(refreshPayload.getJwtID())
                        .expirationTime(refreshPayload.getExpirationTime().getTime()/1000)
                .build());

        return LoginResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(refreshPayload.getToken())
                .build();

    }

    public LoginResponse refreshToken(String refreshToken) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(refreshToken);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (expirationTime.before(new Date())) {
            throw new AppException(INVALID_TOKEN);
        }

        if (!redisTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(INVALID_TOKEN);
        }

        boolean verify = signedJWT.verify(new MACVerifier(jwtService.getSecretKey()));
        if (!verify) {
            throw new AppException(INVALID_TOKEN);
        }
        Long userId = Long.valueOf(signedJWT.getJWTClaimsSet().getSubject());
        String accessToken = jwtService.generateAccessToken(userId);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public void logout(String accessToken,String refreshToken) throws ParseException {
        TokenPayload accessPayload = jwtService.parse(accessToken);
        TokenPayload refreshPayload = jwtService.parse(refreshToken);
        if (accessPayload.getExpirationTime().after(new Date())) {
            long expirationTimeMillis = accessPayload.getExpirationTime().getTime();
            long currentTimeMillis = new Date().getTime();

            long expirationTimeSeconds = (expirationTimeMillis - currentTimeMillis) / 1000;

            RedisToken redisToken = RedisToken.builder()
                    .jwtId(accessPayload.getJwtID())
                    .expirationTime(expirationTimeSeconds)
                    .build();
            redisTokenRepository.save(redisToken);

            if(refreshPayload.getExpirationTime().after(new Date())) {
                redisTokenRepository.deleteById(refreshPayload.getJwtID());
            }
        }
    }
}

