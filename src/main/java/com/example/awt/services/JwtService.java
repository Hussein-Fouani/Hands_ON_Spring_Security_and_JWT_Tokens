package com.example.awt.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
    String ExtractUserName(String username);
     String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);


    String generateRefreshToken(HashMap<String, Object> objectObjectHashMap,  UserDetails  user);
}
