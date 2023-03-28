package com.example.Trainogram.dto;

import lombok.Data;

@Data
public class UserAuthorizationResponseDto {
    private String email;
    private String token;
}
