package com.example.Trainogram.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostRequestDto {
    private String text;
    private List<MultipartFile> files;
}
