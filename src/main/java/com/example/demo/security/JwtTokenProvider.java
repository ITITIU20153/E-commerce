package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;

@Component
public class JwtTokenProvider {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private final long jwtExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        //tao secret key tu config va chuyen sang Byte + thuat toan
        SecretKey secretKey = new SecretKeySpec(
                secret.getBytes(),
                "HmacSHA256"
        );

        //dang ky secret key
        this.jwtEncoder = new NimbusJwtEncoder(
                new com.nimbusds.jose.jwk.source.ImmutableSecret<>(
                        secretKey
                )
        );

        this.jwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        this.jwtExpiration = expiration;
    }

    // 1. Generate JWT
    public String generateToken(UserEntity user) {

        Instant now = Instant.now();
        // System.out.println("JWT expiration: " + jwtExpiration);
        // System.out.println("Issued at: " + now);
        // System.out.println("Expires at: " +
        //         now.plus(jwtExpiration, ChronoUnit.DAYS));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("demo-app")
                .issuedAt(now)
                .expiresAt(now.plus(
                        jwtExpiration,
                        ChronoUnit.DAYS
                ))
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    // 2. Get username from JWT
    public String getUsernameFromToken(String token) {

        Jwt jwt = jwtDecoder.decode(token);

        return jwt.getSubject();
    }

    // 3. Validate JWT
    public boolean validateToken(String token) {

        try {
            jwtDecoder.decode(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            return false;
        }
    }

    // 4. Get role from JWT
    public String getRoleFromToken(String token) {

        Jwt jwt = jwtDecoder.decode(token);

        return jwt.getClaimAsString("role");
    }
}