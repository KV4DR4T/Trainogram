package com.example.trainogram.repository;

import com.example.trainogram.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    Chatroom getChatroomByChatIdAndSenderId(Long chatId, Long userId);

    @Query(value = "select max(chat_id) from chatrooms ", nativeQuery = true)
    Optional<Long> findMaxChatId();
}
