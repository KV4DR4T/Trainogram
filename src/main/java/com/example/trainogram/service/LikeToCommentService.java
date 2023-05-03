package com.example.trainogram.service;

import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.LikeToComment;

import java.util.List;

public interface LikeToCommentService {
    void like(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException, LikeException;

    void unlike(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException;

    List<LikeToComment> getLikes(Long commentId) throws CommentNotFoundException;
}
