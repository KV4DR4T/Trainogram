package com.example.trainogram.service;

import com.example.trainogram.dto.NotificationResponseDto;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> getNotifications(Long userId);
    void notificationCommentLike(Long commentId, String username, Long recipientId) throws UserNotFoundException;
    void notificationPostLike(Long postId, String username, Long recipientId) throws UserNotFoundException;
    void sendNotificationCommentPost(String commentatorUsername, User recipient, Long postId);
    void sendNotificationCommentComment(String commentatorUsername, User recipient);
}
