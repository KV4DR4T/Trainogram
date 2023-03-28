package com.example.Trainogram.controller;

import com.example.Trainogram.dto.NotificationResponseDto;
import com.example.Trainogram.model.Notification;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {


    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotification(JwtUser jwtUser) {
        List<NotificationResponseDto> notifications = notificationService.getNotifications(jwtUser.getId());
        return ResponseEntity.ok(notifications);
    }
}
