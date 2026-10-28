package com.example.awt.services.impl;

import com.example.awt.repository.UserRepository;
import com.example.awt.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("UserName Not Found Exception"));
    }
}
