package com.example.Trainogram.dto;

import com.example.Trainogram.model.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
@Data
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String picturesUrl;
    private String text;
    private LocalDate date;
    private LocalDate lastUpdate;
    private int likes;
    private int comments;
}
