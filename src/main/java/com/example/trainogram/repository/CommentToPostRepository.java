package com.example.trainogram.repository;

import com.example.trainogram.model.CommentToPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentToPostRepository extends JpaRepository<CommentToPost, Long> {

    int countAllByPostId(Long postId);

    List<CommentToPost> findAllByPostId(Long postId);
}
