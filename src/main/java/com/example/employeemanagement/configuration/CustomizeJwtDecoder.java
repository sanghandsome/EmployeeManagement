package com.example.employeemanagement.configuration;

import com.example.employeemanagement.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomizeJwtDecoder implements JwtDecoder {
    @Value("${JWT_SECRET_KEY}")
    private String secret_key;
    private final JwtServiceImpl jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            if (!jwtService.verifyToken(token))
                throw new JwtException("Token expired");

            if(Objects.isNull(nimbusJwtDecoder)){
                SecretKey key = new SecretKeySpec(secret_key.getBytes(StandardCharsets.UTF_8),"HS512");
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
            return nimbusJwtDecoder.decode(token);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
