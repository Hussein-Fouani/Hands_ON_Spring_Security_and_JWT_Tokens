package com.example.awt.mapper;

import com.example.awt.DTO.SignupRequest;
import com.example.awt.entities.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static ModelMapper modelMapper;

    public static User mapToUser(SignupRequest signupRequest){
       return modelMapper.map(signupRequest,User.class);

    }
    public static SignupRequest mapToUserDTO(User user){
        return modelMapper.map(user,SignupRequest.class);

    }
}
