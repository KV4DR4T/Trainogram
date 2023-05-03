package com.example.trainogram.service.implementation;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Notification;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.NotificationRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.facade.SubscriptionFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscriptionFacadeImpl implements SubscriptionFacade {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionFacadeImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendNotification(Long subscribedToId, String username, Long subscriberId) throws UserNotFoundException {
        log.info("Creating notification about subscription");
        User subscribedTo = userRepository.findById(subscribedToId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + subscribedToId + " not found"));
        Notification notification = new Notification();
        notification.setMessage(username + " subscribed to you");
        notification.setRecipient(subscribedTo);
        notificationRepository.save(notification);
        log.info("Notification about subscription {} was created", notification);
    }
}
