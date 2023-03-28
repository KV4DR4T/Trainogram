package com.example.Trainogram.controller;

import com.example.Trainogram.dto.UserAuthorizationRequestDto;
import com.example.Trainogram.dto.UserAuthorizationResponseDto;
import com.example.Trainogram.dto.UserRegistrationRequestDto;
import com.example.Trainogram.exception.EmailIsAlreadyTakenException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
