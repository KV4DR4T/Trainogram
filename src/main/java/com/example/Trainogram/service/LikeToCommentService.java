package com.example.Trainogram.service;

import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.LikeException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.LikeToComment;

import java.util.List;

public interface LikeToCommentService {
    void like(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException, LikeException;
    void unlike(Long commentId, Long userId)  throws CommentNotFoundException, UserNotFoundException;
    List<LikeToComment> getLikes(Long commentId) throws CommentNotFoundException;
}
