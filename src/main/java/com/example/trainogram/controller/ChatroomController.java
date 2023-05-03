package com.example.trainogram.controller;

import com.example.trainogram.dto.ChatroomUsersDto;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.ChatroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-rooms")
public class ChatroomController {
    private final ChatroomService chatroomService;

    public ChatroomController(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @PostMapping
    public void createChatRoom(@RequestBody ChatroomUsersDto users, JwtUser jwtUser)
            throws UserNotFoundException {
        chatroomService.createChatroom(users, jwtUser.getId());
    }
}
