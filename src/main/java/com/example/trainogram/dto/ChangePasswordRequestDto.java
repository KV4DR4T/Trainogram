package com.example.trainogram.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    String code;
    String password;
}
