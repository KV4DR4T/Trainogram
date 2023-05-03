package com.example.trainogram.repository;

import com.example.trainogram.model.LikeToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeToCommentRepository extends JpaRepository<LikeToComment, Long> {
    boolean existsByUserIdAndCommentId(Long userId, Long comment);

    Optional<LikeToComment> findByUserIdAndCommentId(Long userId, Long commentId);

    List<LikeToComment> findAllByCommentId(Long commentId);
}
