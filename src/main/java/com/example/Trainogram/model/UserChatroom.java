package com.example.Trainogram.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.websocket.OnError;
import java.util.List;

@Entity
@Table(name = "user_chatrooms")
@Data
public class UserChatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatRoom;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
