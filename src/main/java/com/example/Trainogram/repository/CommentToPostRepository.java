package com.example.Trainogram.repository;

import com.example.Trainogram.model.CommentToPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentToPostRepository extends JpaRepository<CommentToPost, Long> {

    int countAllByPostId(Long postId);
    List<CommentToPost> findAllByPostId(Long postId);
}
