package com.example.trainogram.repository;

import com.example.trainogram.model.UserChatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatroomRepository extends JpaRepository<UserChatroom, Long> {
}
