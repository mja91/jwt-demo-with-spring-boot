package com.jwt.dto;

import com.jwt.enums.TokenType;
import lombok.Data;

@Data
public class TokenRequest {

    private String token;
    private TokenType type;
}
