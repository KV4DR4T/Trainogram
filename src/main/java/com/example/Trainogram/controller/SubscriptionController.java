package com.example.Trainogram.controller;

import com.example.Trainogram.dto.UserResponseDto;
import com.example.Trainogram.exception.SubscriptionNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.User;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{subscribeToId}")
    public ResponseEntity<?> subscribe(@PathVariable Long subscribeToId,JwtUser jwtUser) throws UserNotFoundException, SubscriptionNotFoundException {
        subscriptionService.subscribe(jwtUser.getId(), subscribeToId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{subscribeToId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long subscribeToId, JwtUser jwtUser) throws UserNotFoundException, SubscriptionNotFoundException {
        subscriptionService.unsubscribe(jwtUser.getId(), subscribeToId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/subscribers")
    public ResponseEntity<List<User>> getSubscribers(JwtUser jwtUser) {
        List<User> users = subscriptionService.subscribers(jwtUser.getId());
        return ResponseEntity.ok(users);
    }

    @GetMapping(value="/subscribed")
    public ResponseEntity<List<UserResponseDto>> getSubscribed(JwtUser jwtUser) throws UserNotFoundException {
        List<UserResponseDto> users=subscriptionService.subscribed(jwtUser.getId());
        return ResponseEntity.ok(users);
    }
}
