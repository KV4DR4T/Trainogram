package com.example.trainogram.config;

import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.ChatMessage;
import com.example.trainogram.model.Chatroom;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.ChatMessageRepository;
import com.example.trainogram.repository.ChatroomRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.UserService;
import com.example.trainogram.util.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/chat/{chatId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
@Slf4j
public class ChatEndpoint {
    public Session session;

    public ChatEndpoint(ChatroomRepository chatroomRepository, UserService userService, ChatMessageRepository chatMessageRepository) {
        this.chatroomRepository = chatroomRepository;
        this.userService = userService;
        this.chatMessageRepository = chatMessageRepository;
    }

    private final ChatroomRepository chatroomRepository;
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;
    private User currentUser;
    private Chatroom chatroom;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();

    public ChatEndpoint() {
        this.chatroomRepository =  SpringContext.getContext().getBean(ChatroomRepository.class);
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.chatMessageRepository = SpringContext.getContext().getBean(ChatMessageRepository.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") Long chatId) throws UserNotFoundException {
        this.session = session;


        currentUser = userService.findByEmail(session.getUserPrincipal().getName());
        chatroom = chatroomRepository.getChatroomByChatIdAndSenderId(chatId, currentUser.getId());


        chatEndpoints.add(this);
        List<ChatMessage> messages = chatMessageRepository.getAllByChatId(chatroom.getChatId());
        messages.forEach(message -> {
            try {
                broadcastPrevious(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (EncodeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnMessage
    public void onMessage(Session session, String chatMessage) throws IOException, EncodeException {
//        ChatMessage chatMessage1 = new ChatMessage();
//        chatMessage1.setChatId(chatroom.getChatId());
//        chatMessage1.setSender(currentUser);
//        chatMessage1.setText(chatMessage);
//        chatMessage1.setRecipient(chatroom.getRecipient());
//        chatMessageRepository.save(chatMessage1);
//        broadcast(chatMessage1);
        List<Long> recipients = chatroom.getRecipients()/*.stream().map(User::getId).collect(Collectors.toList())*/;
        recipients.add(currentUser.getId());

        recipients.forEach(recipient -> {
            ChatMessage message = new ChatMessage();
            message.setChatId(chatroom.getChatId());
            message.setSender(currentUser);
            message.setText(chatMessage);
            try {
                message.setRecipient(userService.findById(recipient));
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            chatMessageRepository.save(message);
            try {
                broadcast(new ChatMessage(message.getId(), message.getText(), message.getChatId(), message.getSender(), message.getRecipient()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (EncodeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnClose
    public void onClose() {
        chatEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }


    private void broadcast(ChatMessage chatMessage) throws IOException, EncodeException {
        chatEndpoints.forEach(chatEndpoint -> {
            synchronized (this) {
                if (!this.chatroom.getId().equals(chatEndpoint.chatroom.getId())) {
                    if (!this.currentUser.equals(chatEndpoint.currentUser)) {
                        try {
                            chatEndpoint.session.getBasicRemote().sendObject(chatMessage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (EncodeException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void broadcastPrevious(ChatMessage chatMessage) throws IOException, EncodeException {
        chatEndpoints.forEach(chatEndpoint -> {
            synchronized (this) {
                if (this.chatroom.getId().equals(chatEndpoint.chatroom.getId())) {
                    try {
                        chatEndpoint.session.getBasicRemote().sendObject(chatMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (EncodeException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }
}
