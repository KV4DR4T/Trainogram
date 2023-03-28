package com.example.Trainogram.dto;

import com.example.Trainogram.model.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String avatar;
    private String email;
    private Role role;
    private int subscribers;
    private int subscriptions;
    private int posts;
}
