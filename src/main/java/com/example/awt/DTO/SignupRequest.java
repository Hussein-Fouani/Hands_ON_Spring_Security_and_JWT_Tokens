package com.example.awt.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    private Integer Id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
