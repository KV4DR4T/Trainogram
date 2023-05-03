package com.example.trainogram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "likes_to_post")
@Data
public class LikeToPost {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
