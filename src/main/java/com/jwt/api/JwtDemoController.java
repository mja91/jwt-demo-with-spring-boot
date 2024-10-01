package com.jwt.api;

import com.jwt.dto.TokenRequest;
import com.jwt.dto.TokenResponse;
import com.jwt.dto.TokenValidation;
import com.jwt.enums.TokenType;
import com.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jwt")
public class JwtDemoController {

    @PostMapping("/{userId}")
    public ResponseEntity<TokenResponse> generateJwt(@PathVariable(value = "userId") String userId) {
        final String accessToken = JwtUtil.generateToken(userId, TokenType.ACCESS);
        final String refreshToken = JwtUtil.generateToken(userId, TokenType.REFRESH);
        TokenResponse response = TokenResponse.of(userId, accessToken, refreshToken, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/validation")
    public ResponseEntity<TokenValidation> validateJwt(@RequestBody TokenRequest request) {
        final String token = request.getToken();
        final TokenType type = request.getType();

        if (!JwtUtil.validateJwt(token, type)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        final String userId = JwtUtil.getUserIdFromJwt(token, type);
        TokenValidation response = TokenValidation.of(userId, token, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reissuance")
    public ResponseEntity<TokenResponse> reissuanceJwt(@RequestBody TokenRequest tokenRequest) {
        final String clientRefreshToken = tokenRequest.getToken();
        if (!JwtUtil.validateJwt(clientRefreshToken, TokenType.REFRESH)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        final String userId = JwtUtil.getUserIdFromJwt(clientRefreshToken, TokenType.REFRESH);
        final String newAccessToken = JwtUtil.generateToken(userId, TokenType.ACCESS);
        final String newRefreshToken = JwtUtil.generateToken(userId, TokenType.REFRESH);

        TokenResponse response = TokenResponse.of(userId, newAccessToken, newRefreshToken, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
