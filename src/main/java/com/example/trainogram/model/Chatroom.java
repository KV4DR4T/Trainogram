package com.example.trainogram.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "chatrooms")
@Data
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //todo: remove it
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    //    @ManyToOne
//    @JoinColumn(name = "recipient_id")
//    private User recipient;

//    @Column(name = "chat_id")
    private Long chatId;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
    private List<UserChatroom> userChatrooms;


    /*public List<User> getRecipients() {
        return userChatrooms.stream()
                .map(UserChatroom::getUser)
                .collect(Collectors.toList());
    }*/

    @ElementCollection()
    @Column(name = "recipient_id")
    @CollectionTable(name = "chatroom_recipients_id", joinColumns = @JoinColumn(name = "chatroom_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> recipients;
}
