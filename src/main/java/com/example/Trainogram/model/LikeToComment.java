package com.example.Trainogram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "likes_to_comment")
@Data
public class LikeToComment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentToPost comment;
}
