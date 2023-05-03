package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.NotificationResponseDto;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.SponsoredPost;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.NotificationRepository;
import com.example.trainogram.repository.SponsoredPostRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final SponsoredPostRepository sponsoredPostRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, SponsoredPostRepository sponsoredPostRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.sponsoredPostRepository = sponsoredPostRepository;

        this.userRepository = userRepository;
    }

    @Override
    public List<NotificationResponseDto> getNotifications(Long userId) {
        //        if (!notifications.isEmpty()){
//            notificationRepository.deleteAll(notifications);
//        }
        log.info("Getting notifications for user with id: {} ", userId);

        List<Notification> notifications = notificationRepository.findByRecipientId(userId);

        List<NotificationResponseDto> responseDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationResponseDto responseDto = new NotificationResponseDto();
            responseDto.setMessage(notification.getMessage());
            responseDto.setId(notification.getId());
            responseDto.setDate(notification.getDate());
            responseDtos.add(responseDto);
        }
        log.info("Notifications for user with id: {} are: {}", userId, responseDtos);
        return responseDtos;
    }


    @Override
    public void notificationCommentLike(Long commentId, String username, Long recipientId) throws UserNotFoundException {
        log.info("Creating notification about comment like");
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new UserNotFoundException("User with id: " + recipientId + " not found"));
        Notification notification = new Notification();
        notification.setMessage(username + " liked your comment");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        log.info("Notification about comment like {} was created",notification);
    }

    @Override
    public void notificationPostLike(Long postId, String username, Long recipientId) throws UserNotFoundException {
        log.info("Creating notification about post like");
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new UserNotFoundException("User with id: " + recipientId + " not found"));
        Notification notification = new Notification();
        notification.setMessage(username + " liked your post");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        if (sponsoredPostRepository.existsByPostId(postId)) {
            SponsoredPost sponsoredPost = sponsoredPostRepository.findByPostId(postId).get();
            Notification notificationToSponsor = new Notification();
            notificationToSponsor.setMessage(username + " liked post you sponsored");
            notificationToSponsor.setRecipient(sponsoredPost.getSponsor());
            notificationToSponsor.setDate(LocalDate.now());
            notificationRepository.save(notificationToSponsor);
        }
        log.info("Notification about post like {} was created",notification);
    }

    public void sendNotificationCommentPost(String commentatorUsername, User recipient, Long postId) {
        log.info("Creating notification about comment post");
        Notification notification = new Notification();
        notification.setMessage(commentatorUsername + " commented your post");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        if (sponsoredPostRepository.existsByPostId(postId)) {
            SponsoredPost sponsoredPost = sponsoredPostRepository.findByPostId(postId).get();
            Notification notificationToSponsor = new Notification();
            notificationToSponsor.setMessage(commentatorUsername + " commented post you sponsored");
            notificationToSponsor.setRecipient(sponsoredPost.getSponsor());
            notificationToSponsor.setDate(LocalDate.now());
            notificationRepository.save(notificationToSponsor);
        }
        log.info("Notification about comment post {} was created",notification);
    }

    public void sendNotificationCommentComment(String commentatorUsername, User recipient) {
        log.info("Creating notification about comment comment");
        Notification notification = new Notification();
        notification.setMessage(commentatorUsername + " commented your comment");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        log.info("Notification about comment comment {} was created",notification);
    }
}
