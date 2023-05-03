package com.example.trainogram.model;

import lombok.Data;

import javax.persistence.*;

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
