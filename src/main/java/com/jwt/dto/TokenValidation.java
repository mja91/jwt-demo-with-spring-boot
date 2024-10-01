package com.jwt.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenValidation {

    private String userId;
    private String token;
    private boolean isActive;

    @Builder
    public TokenValidation(String userId,
                           String token,
                           boolean isActive) {
        this.userId = userId;
        this.token = token;
        this.isActive = isActive;
    }

    public static TokenValidation of(String userId,
                                     String token,
                                     boolean isActive) {
        return TokenValidation.builder()
                .userId(userId)
                .token(token)
                .isActive(isActive)
                .build();
    }
}
