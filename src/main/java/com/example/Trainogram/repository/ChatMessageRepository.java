package com.example.Trainogram.repository;

import com.example.Trainogram.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> getAllByChatId(Long chatId);
}
