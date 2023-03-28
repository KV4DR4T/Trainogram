package com.example.Trainogram.service.facade;

import com.example.Trainogram.exception.UserNotFoundException;

public interface SubscriptionFacade {
    void sendNotification(Long subscribedToId,String username,Long subscriberId) throws UserNotFoundException;
}
