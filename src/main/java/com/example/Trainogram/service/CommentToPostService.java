package com.example.Trainogram.service;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.CommentToPost;

import java.util.List;

public interface CommentToPostService {
    void comment(Long userId, Long postId, CommentRequestDto requestDto) throws PostNotFoundException, UserNotFoundException, InvalidParamException;
    void deleteComment(Long userId, Long commentId) throws UserNotFoundException, CommentNotFoundException;
    List<CommentToPost> getComments(Long postId);
}
