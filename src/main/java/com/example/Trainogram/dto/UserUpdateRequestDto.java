package com.example.Trainogram.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequestDto {
    private MultipartFile avatar;
    private String username;
    private String email;
}
