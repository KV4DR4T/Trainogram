package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.ChatroomUsersDto;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Chatroom;
import com.example.trainogram.model.UserChatroom;
import com.example.trainogram.repository.ChatroomRepository;
import com.example.trainogram.repository.UserChatroomRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.ChatroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatroomServiceImpl implements ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final UserChatroomRepository userChatroomRepository;
    private final UserRepository userRepository;

    public ChatroomServiceImpl(ChatroomRepository chatroomRepository, UserChatroomRepository userChatroomRepository, UserRepository userRepository) {
        this.chatroomRepository = chatroomRepository;
        this.userChatroomRepository = userChatroomRepository;
        this.userRepository = userRepository;
    }

    @Override

//    @Transactional
    public void createChatroom(ChatroomUsersDto users, Long senderId) throws UserNotFoundException {
        log.info("Creating chatroom with users: " + users.getUsers());

        if (!userRepository.existsById(senderId)) {
            log.warn("User with id: {} not found", senderId);
            throw new UserNotFoundException("User with id: " + senderId + " not found");
        }

        Long chatId = chatroomRepository.findMaxChatId().orElse(0L);
        chatId = chatId + 1;
        List<Long> members = new ArrayList<>();
        members.add(senderId);
        members.addAll(users.getUsers());

        for (int i = 0; i < members.size(); i++) {
            List<Long> membersList = new ArrayList<>(members);

            membersList.remove(i);
            Chatroom chatroom = new Chatroom();
            chatroom.setChatId(chatId);
            chatroom.setSender(userRepository.getById(members.get(i)));
            chatroom.setRecipients(membersList);
            chatroomRepository.save(chatroom);

            UserChatroom userChatroom = new UserChatroom();
            userChatroom.setUser(userRepository.getById(members.get(i)));
            userChatroom.setChatRoom(chatroom);
            userChatroomRepository.save(userChatroom);

            membersList.clear();
            log.info("Chat {} was created", chatroom);
        }

    }
}
