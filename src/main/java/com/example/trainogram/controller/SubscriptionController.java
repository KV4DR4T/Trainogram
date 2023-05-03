package com.example.trainogram.controller;

import com.example.trainogram.dto.UserResponseDto;
import com.example.trainogram.exception.SubscriptionNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.SubscriptionService;
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
    public ResponseEntity<?> subscribe(@PathVariable Long subscribeToId, JwtUser jwtUser) throws UserNotFoundException, SubscriptionNotFoundException {
        subscriptionService.subscribe(jwtUser.getId(), subscribeToId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subscribeToId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long subscribeToId, JwtUser jwtUser) throws UserNotFoundException, SubscriptionNotFoundException {
        subscriptionService.unsubscribe(jwtUser.getId(), subscribeToId);
        return ResponseEntity.ok().build();
    }



    @GetMapping(value = "/subscription")
    public ResponseEntity<List<UserResponseDto>> getSubscribers(JwtUser jwtUser, @RequestParam String subscriptionRequest) throws UserNotFoundException {
        List<UserResponseDto> users = subscriptionService.subscription(jwtUser.getId(), subscriptionRequest);
        return ResponseEntity.ok(users);
    }

}
