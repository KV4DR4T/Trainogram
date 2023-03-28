package com.example.Trainogram.repository;

import com.example.Trainogram.model.CommentToComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentToCommentRepository extends JpaRepository<CommentToComment,Long> {
    int countAllByCommentId(Long commentId);
    List<CommentToComment> findAllByCommentId(Long commentId);
}
