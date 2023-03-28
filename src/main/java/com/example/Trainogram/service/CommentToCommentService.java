package com.example.Trainogram.service;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.CommentToComment;

import java.util.List;

public interface CommentToCommentService {
    void comment(Long userId, Long commentId, CommentRequestDto requestDto) throws CommentNotFoundException, UserNotFoundException, InvalidParamException;
    void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException;
    List<CommentToComment> getComments(Long commentId);
}
