package com.example.Trainogram.repository;

import com.example.Trainogram.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    Chatroom getChatroomByChatIdAndSenderId(Long chatId, Long userId);
    @Query(value = "select max(chat_id) from chatrooms ",nativeQuery = true)
    Optional<Long> findMaxChatId();
}
