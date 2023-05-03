package com.example.trainogram.controller;

import com.example.trainogram.dto.UserAuthorizationRequestDto;
import com.example.trainogram.dto.UserAuthorizationResponseDto;
import com.example.trainogram.dto.UserRegistrationRequestDto;
import com.example.trainogram.exception.EmailIsAlreadyTakenException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthorizationResponseDto> login(@RequestBody UserAuthorizationRequestDto requestDto) throws UserNotFoundException {
        UserAuthorizationResponseDto response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@ModelAttribute UserRegistrationRequestDto requestDto) throws InvalidParamException, EmailIsAlreadyTakenException, IOException {
        userService.register(requestDto);
        return ResponseEntity.ok().build();
    }
}
