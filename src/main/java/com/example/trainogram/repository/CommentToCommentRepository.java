package com.example.trainogram.repository;

import com.example.trainogram.model.CommentToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentToCommentRepository extends JpaRepository<CommentToComment, Long> {
    int countAllByCommentId(Long commentId);

    List<CommentToComment> findAllByCommentId(Long commentId);
}
