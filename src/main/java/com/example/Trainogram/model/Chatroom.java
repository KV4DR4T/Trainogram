package com.example.Trainogram.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chatrooms")
@Data
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
//    @ManyToOne
//    @JoinColumn(name = "recipient_id")
//    private User recipient;
    @Column(name = "chat_id")
    private Long chatId;

    @OneToMany(mappedBy = "chatRoom")
    List<UserChatroom> userChatrooms;

    @ElementCollection()
    @Column(name = "recipient_id")
    @CollectionTable(name = "chatroom_recipients", joinColumns = @JoinColumn(name = "chatroom_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> recipients;
}
