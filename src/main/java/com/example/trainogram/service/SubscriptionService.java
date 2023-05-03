package com.example.trainogram.service;

import com.example.trainogram.dto.UserResponseDto;
import com.example.trainogram.exception.SubscriptionNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;

import java.util.List;

public interface SubscriptionService {
    void subscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException;

    void unsubscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException;


    List<UserResponseDto> subscription(Long userId, String subscriptionRequest) throws UserNotFoundException;
}
