package com.example.Trainogram.config;

import com.example.Trainogram.model.ChatMessage;
import com.example.Trainogram.model.Chatroom;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.ChatMessageRepository;
import com.example.Trainogram.repository.ChatroomRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.util.SpringContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat/{chatId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
    public Session session;
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private User currentUser;
    private Chatroom chatroom;
    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();

    public ChatEndpoint() {
        this.chatroomRepository = (ChatroomRepository) SpringContext.getContext().getBean("chatroomRepository");
        this.userRepository = (UserRepository) SpringContext.getContext().getBean("userRepository");
        this.chatMessageRepository = (ChatMessageRepository) SpringContext.getContext().getBean("chatMessageRepository");
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") Long chatId) {
        this.session = session;

        currentUser = userRepository.findByEmail(session.getUserPrincipal().getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
        List<Long> recipients=chatroom.getRecipients();
        recipients.add(currentUser.getId());

        recipients.forEach(recipient->{
            ChatMessage message = new ChatMessage();
            message.setChatId(chatroom.getChatId());
            message.setSender(currentUser);
            message.setText(chatMessage);
            message.setRecipient(userRepository.findById(recipient).get());
            chatMessageRepository.save(message);
            try {
                broadcast(new ChatMessage(message.getId(),message.getText(), message.getChatId(), message.getSender(), message.getRecipient()));
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
