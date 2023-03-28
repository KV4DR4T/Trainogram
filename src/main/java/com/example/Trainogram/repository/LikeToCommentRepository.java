package com.example.Trainogram.repository;

import com.example.Trainogram.model.LikeToComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeToCommentRepository extends JpaRepository<LikeToComment, Long> {
    boolean existsByUserIdAndCommentId(Long userId, Long comment);
    Optional<LikeToComment> findByUserIdAndCommentId(Long userId, Long commentId);
    List<LikeToComment> findAllByCommentId(Long commentId);
}
