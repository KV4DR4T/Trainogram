package com.example.Trainogram.repository;

import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByRecipientId(Long recipientId);
    boolean existsByContentTypeAndContentIdAndRecipientId(ContentType contentType,Long contentId,Long recipientId);
}
