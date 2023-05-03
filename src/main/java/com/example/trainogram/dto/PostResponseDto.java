package com.example.trainogram.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostResponseDto {
    private Long id;
    private UserResponseDto user;
    private String picturesUrl;
    private String text;
    private LocalDate date;
    private LocalDate lastUpdate;
    private int likes;
    private int comments;
}
