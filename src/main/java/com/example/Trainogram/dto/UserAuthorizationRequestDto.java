package com.example.Trainogram.dto;

import lombok.Data;

@Data
public class UserAuthorizationRequestDto {
    private String email;
    private String password;

}
