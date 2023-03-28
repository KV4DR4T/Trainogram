package com.example.Trainogram.service;

import com.example.Trainogram.exception.LikeException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.LikeToPost;

import java.util.List;

public interface LikeToPostService {
    void like(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException, LikeException;
    void unlike(Long postId, Long userId) throws PostNotFoundException;
    List<LikeToPost> getLikes(Long posId) throws PostNotFoundException;
}
