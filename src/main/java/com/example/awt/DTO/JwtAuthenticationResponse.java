package com.example.awt.DTO;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private  String refreshToken;
}
