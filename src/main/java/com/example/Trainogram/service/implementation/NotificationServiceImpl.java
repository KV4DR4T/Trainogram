package com.example.Trainogram.service.implementation;

import com.example.Trainogram.dto.NotificationResponseDto;
import com.example.Trainogram.model.Notification;
import com.example.Trainogram.repository.NotificationRepository;
import com.example.Trainogram.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationResponseDto> getNotifications(Long userId)  {
        //        if (!notifications.isEmpty()){
//            notificationRepository.deleteAll(notifications);
//        }

        List<Notification> notifications= notificationRepository.findByRecipientId(userId);
        List<NotificationResponseDto> responseDtos = new ArrayList<>();
        for (Notification notification:notifications){
            NotificationResponseDto responseDto = new NotificationResponseDto();
            responseDto.setMessage(notification.getMessage());
            responseDto.setId(notification.getId());
            responseDto.setDate(notification.getDate());
            responseDto.setRecipientId(notification.getRecipient().getId());
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}
