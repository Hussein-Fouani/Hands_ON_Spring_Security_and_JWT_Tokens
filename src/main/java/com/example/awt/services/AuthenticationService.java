package com.example.awt.services;

import com.example.awt.DTO.JwtAuthenticationResponse;
import com.example.awt.DTO.RefreshTokenRequest;
import com.example.awt.DTO.SigninRequest;
import com.example.awt.DTO.SignupRequest;
import com.example.awt.entities.User;

public interface AuthenticationService {
    User Signup(SignupRequest signupRequest);
    JwtAuthenticationResponse SignIn(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
