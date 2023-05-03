package com.example.trainogram.service;

import com.example.trainogram.dto.ChatroomUsersDto;
import com.example.trainogram.exception.UserNotFoundException;

import java.util.List;

public interface ChatroomService {
    void createChatroom(ChatroomUsersDto users, Long userId) throws UserNotFoundException;
}
