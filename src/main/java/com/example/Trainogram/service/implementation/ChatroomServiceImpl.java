package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.Chatroom;
import com.example.Trainogram.model.User;
import com.example.Trainogram.model.UserChatroom;
import com.example.Trainogram.repository.ChatroomRepository;
import com.example.Trainogram.repository.UserChatroomRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.ChatroomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
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
    @Transactional
    public void createChatroom(List<Long> recipients, Long senderId) throws UserNotFoundException {
        User user = userRepository.findById(senderId).orElseThrow(()-> new UserNotFoundException("User with id: "+ senderId+" not found."));
        Long chatId = chatroomRepository.findMaxChatId().orElse(0L);
        chatId=chatId+1;
//        Chatroom chatroomSender = new Chatroom();
//        chatroomSender.setSender(user);
//        chatroomSender.setRecipient(recipient);
//        chatroomSender.setChatId(chatId);
//        chatroomRepository.save(chatroomSender);
//
//        Chatroom chatroomRecipient = new Chatroom();
//        chatroomRecipient.setSender(recipient);
//        chatroomRecipient.setRecipient(user);
//        chatroomRecipient.setChatId(chatId);
//        chatroomRepository.save(chatroomRecipient);
//
//        UserChatroom userChatroomSender = new UserChatroom();
//        userChatroomSender.setUser(user);
//        userChatroomSender.setChatRoom(chatroomSender);
//        userChatroomRepository.save(userChatroomSender);
//
//        UserChatroom userChatroomRecipient = new UserChatroom();
//        userChatroomRecipient.setUser(recipient);
//        userChatroomRecipient.setChatRoom(chatroomRecipient);
//        userChatroomRepository.save(userChatroomRecipient);

        List<Long> members=new ArrayList<>();
        members.add(senderId);
        members.addAll(recipients);

        for(int i=0;i<members.size();i++){
            List<Long> membersList=new ArrayList<>(members);

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
        }

    }
}
