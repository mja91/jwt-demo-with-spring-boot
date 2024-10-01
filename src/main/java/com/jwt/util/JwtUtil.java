package com.jwt.util;

import com.jwt.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtUtil {

    // Secret Keys(Recommend setting as an environment variable in production)
    private static final String ACCESS_TOKEN_SECRET_KEY_STRING = "my-access-token-secret-key-which-is-very-long-and-secure!1234567890";
    private static final String REFRESH_TOKEN_SECRET_KEY_STRING = "my-refresh-token-secret-key-which-is-very-long-and-secure!1234567890";
    // JWT expiration times
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 21;

    // Generate Access Token
    public static String generateToken(String userId, TokenType type) {
        Date expirationDate =
                new Date(System.currentTimeMillis() + (type == TokenType.ACCESS ? ACCESS_TOKEN_EXPIRATION_TIME : REFRESH_TOKEN_EXPIRATION_TIME));
        return Jwts.builder()
                .setSubject(userId) // Set subject to userId
                .setIssuedAt(new Date()) // Set issued time
                .setExpiration(expirationDate) // Set expiration time
                .signWith(getSecretKey(type)) // Sign with secret key
                .compact(); // Build the token
    }

    // Validate JWT
    public static boolean validateJwt(String token, TokenType type) {
        try {
            // Automatically validates both signature and expiration time
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(type)) // Set secret key
                    .build()
                    .parseClaimsJws(token); // Validate signature and expiration
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired");
            return false;
        } catch (JwtException e) {
            log.error("Invalid Token Signature");
            return false;
        }
    }

    // Extract claims from JWT (e.g., userId)
    public static String getUserIdFromJwt(String token, TokenType type) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey(type))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private static SecretKey getSecretKey(TokenType type) {
        byte[] keyBytes = Base64.getEncoder().encode(type == TokenType.ACCESS ?
                ACCESS_TOKEN_SECRET_KEY_STRING.getBytes() : REFRESH_TOKEN_SECRET_KEY_STRING.getBytes());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}