package com.example.trainogram.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationResponseDto {
    private Long id;
    private LocalDate date;
    private String message;

}
