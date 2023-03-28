package com.example.Trainogram.service;

import com.example.Trainogram.dto.UserResponseDto;
import com.example.Trainogram.exception.SubscriptionNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscriptionService {
    void subscribe(Long userId,Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException;
    void unsubscribe(Long userId,Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException;

    List<User> subscribers(Long userId) ;
    List<UserResponseDto> subscribed(Long userId) throws UserNotFoundException;
}
