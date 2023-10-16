package com.example.awt.services.impl;

import com.example.awt.DTO.JwtAuthenticationResponse;
import com.example.awt.DTO.RefreshTokenRequest;
import com.example.awt.DTO.SigninRequest;
import com.example.awt.DTO.SignupRequest;
import com.example.awt.entities.Role;
import com.example.awt.entities.User;
import com.example.awt.repository.UserRepository;
import com.example.awt.services.AuthenticationService;
import com.example.awt.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    public User Signup(SignupRequest signupRequest){
        User user = new User();
        user.setRole(Role.USER);
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        return user;
    }

    public JwtAuthenticationResponse SignIn(SigninRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalArgumentException("Email Invalid" ));
      var jwt=  jwtService.generateToken(user);
        var refreshtoken =jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshtoken);
        return jwtAuthenticationResponse;

    }
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request){
        String userEmail = jwtService.ExtractUserName(request.getToken()) ;
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(request.getToken(),user)){
            var jwt =jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(request.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
