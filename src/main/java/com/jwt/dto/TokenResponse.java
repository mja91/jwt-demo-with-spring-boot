package com.jwt.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenResponse {

    private String userId;
    private String accessToken;
    private String refreshToken;
    private boolean isActive;

    @Builder
    public TokenResponse(String userId,
                         String accessToken,
                         String refreshToken,
                         boolean isActive) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isActive = isActive;
    }

    public static TokenResponse of(String userId,
                                   String accessToken,
                                   String refreshToken,
                                   boolean isActive) {
        return TokenResponse.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isActive(isActive)
                .build();
    }
}
