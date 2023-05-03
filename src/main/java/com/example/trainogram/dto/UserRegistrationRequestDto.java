package com.example.trainogram.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRegistrationRequestDto {
    private MultipartFile avatar;
    private String username;
    private String email;
    private String password;
}
