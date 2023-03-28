package com.example.Trainogram.repository;

import com.example.Trainogram.model.LikeToPost;
import com.example.Trainogram.model.Post;
import com.example.Trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeToPostRepository extends JpaRepository<LikeToPost,Long> {
    int countAllByPostId(Long postId);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    Optional<LikeToPost> findByUserIdAndPostId(Long userId, Long postId);
    List<LikeToPost> findAllByPostId(Long postId);
}
