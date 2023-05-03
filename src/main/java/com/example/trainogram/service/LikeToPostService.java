package com.example.trainogram.service;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.LikeToPost;

import java.util.List;

public interface LikeToPostService {
    void like(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException, LikeException;

    void unlike(Long postId, Long userId) throws PostNotFoundException;

    List<LikeToPost> getLikes(Long posId) throws PostNotFoundException;
}
