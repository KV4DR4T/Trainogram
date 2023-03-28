package com.example.Trainogram.service;

import com.example.Trainogram.dto.NotificationResponseDto;
import com.example.Trainogram.model.Notification;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> getNotifications(Long userId);
}
