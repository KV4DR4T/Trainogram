package com.example.Trainogram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments_to_post")
@Data
public class CommentToPost {
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
    @Column(name = "text")
    private String text;
}
