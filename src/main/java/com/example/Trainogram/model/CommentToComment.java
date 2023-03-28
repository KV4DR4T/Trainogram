package com.example.Trainogram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments_to_comment")
@Data
public class CommentToComment {
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
    @Column(name = "text")
    private String text;
}
