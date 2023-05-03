package com.example.trainogram.dto;

import lombok.Data;

@Data
public class UserAuthorizationRequestDto {
    private String email;
    private String password;

}
