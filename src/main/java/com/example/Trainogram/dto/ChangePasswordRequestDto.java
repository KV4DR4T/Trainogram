package com.example.Trainogram.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    String code;
    String password;
}
