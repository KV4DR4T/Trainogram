package com.example.trainogram.repository;

import com.example.trainogram.model.LikeToPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeToPostRepository extends JpaRepository<LikeToPost, Long> {
    int countAllByPostId(Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<LikeToPost> findByUserIdAndPostId(Long userId, Long postId);

    List<LikeToPost> findAllByPostId(Long postId);
}
