package com.example.Trainogram.controller;

import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.ChatroomService;
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
    public void createChatRoom(@RequestParam("users") List<Long> users, JwtUser jwtUser) throws UserNotFoundException {
         chatroomService.createChatroom(users,jwtUser.getId());
    }
}
