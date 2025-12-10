package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.TokenPayload;
import com.example.employeemanagement.repository.RedisTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl {
    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;
    private final RedisTokenRepository redisTokenRepository;

    public String generateAccessToken(Long userId) throws JOSEException {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        log.info(secretKey);
        //Payload
        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(10, ChronoUnit.MINUTES));
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("backend-service")
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        //Signature
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey));
        return jwsObject.serialize();
    }

    public TokenPayload generateRefreshToken(Long userId) throws JOSEException {
        //Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Payload
        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(14, ChronoUnit.DAYS));
        String jwtID = UUID.randomUUID().toString();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("backend-service")
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .jwtID(jwtID)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        //Signature
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey));
        return TokenPayload.builder()
                .token(jwsObject.serialize())
                .expirationTime(expirationTime)
                .jwtID(jwtID)
                .build();
    }

    public boolean verifyToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if (expirationTime.before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        if (redisTokenRepository.existsById(jwtId)){
            throw new RuntimeException("Token is logged");
        }
        return true;
    }

    public TokenPayload parse(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return TokenPayload.builder()
                .jwtID(signedJWT.getJWTClaimsSet().getJWTID())
                .expirationTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                .token(token)
                .build();
    }
}
