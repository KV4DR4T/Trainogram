package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Notification;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.NotificationRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.facade.SubscriptionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionFacadeImpl implements SubscriptionFacade {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionFacadeImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendNotification(Long subscribedToId,String username,Long subscriberId) throws UserNotFoundException {
        User subscribedTo = userRepository.findById(subscribedToId).orElseThrow(()-> new UserNotFoundException("User with id: "+ subscribedToId +" not found"));
        Notification notification = new Notification();
        notification.setMessage(username+" subscribed to you");
        notification.setRecipient(subscribedTo);
        notificationRepository.save(notification);
    }
}
