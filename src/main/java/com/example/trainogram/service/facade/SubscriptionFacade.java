package com.example.trainogram.service.facade;

import com.example.trainogram.exception.UserNotFoundException;

public interface SubscriptionFacade {
    void sendNotification(Long subscribedToId, String username, Long subscriberId) throws UserNotFoundException;
}
