package com.example.trainogram.service;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToPost;

import java.util.List;

public interface CommentToPostService {
    void comment(Long userId,  CommentRequestDto requestDto) throws PostNotFoundException, UserNotFoundException, InvalidParamException;

    void deleteComment(Long userId, Long commentId) throws UserNotFoundException, CommentNotFoundException;

    List<CommentToPost> getComments(Long postId);
    CommentToPost getComment(Long commentId) throws CommentNotFoundException;
}
