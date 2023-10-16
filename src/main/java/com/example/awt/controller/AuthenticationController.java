package com.example.awt.controller;

import com.example.awt.DTO.JwtAuthenticationResponse;
import com.example.awt.DTO.RefreshTokenRequest;
import com.example.awt.DTO.SigninRequest;
import com.example.awt.DTO.SignupRequest;
import com.example.awt.entities.User;
import com.example.awt.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(service.Signup(signupRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(service.SignIn(signinRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest  signinRequest){
        return ResponseEntity.ok(service.refreshToken(signinRequest));
    }
}
