package com.example.trainogram.service;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToComment;

import java.util.List;

public interface CommentToCommentService {
    void comment(Long userId, CommentRequestDto requestDto) throws CommentNotFoundException, UserNotFoundException, InvalidParamException;

    void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException;

    List<CommentToComment> getComments(Long commentId);
}
