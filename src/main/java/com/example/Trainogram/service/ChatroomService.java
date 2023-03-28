package com.example.Trainogram.service;

import com.example.Trainogram.exception.UserNotFoundException;

import java.util.List;

public interface ChatroomService {
    void createChatroom(List<Long> recipients, Long userId) throws UserNotFoundException;
}
