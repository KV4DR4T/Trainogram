package com.example.trainogram.service;

import com.example.trainogram.dto.FeedbackResponseDto;
import com.example.trainogram.dto.PostRequestDto;
import com.example.trainogram.dto.PostResponseDto;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.NotEnoughRightsException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post createPost(Long userId, PostRequestDto requestDto) throws UserNotFoundException, InvalidParamException, IOException;

    void deletePost(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException;

    void updatePost(Long postId, Long userId, PostRequestDto requestDto) throws PostNotFoundException, IOException, UserNotFoundException, InvalidParamException;

    Post createSponsoredPost(Long userId, PostRequestDto requestDto, Long sponsorId) throws UserNotFoundException, InvalidParamException, IOException;

    List<PostResponseDto> showAllUserPosts(Long userId) throws PostNotFoundException, UserNotFoundException;

    List<Post> showRecommendation(Long userId) throws UserNotFoundException;

    FeedbackResponseDto showStatistics(Long userId, Long postId) throws PostNotFoundException, UserNotFoundException, NotEnoughRightsException;

    PostResponseDto showPost(Long postId) throws PostNotFoundException, UserNotFoundException;
}
