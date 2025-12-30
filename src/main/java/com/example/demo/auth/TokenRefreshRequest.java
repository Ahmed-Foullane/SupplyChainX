package com.example.demo.auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
