package com.example.Trainogram.service;

import com.example.Trainogram.dto.FeedbackResponseDto;
import com.example.Trainogram.dto.PostRequestDto;
import com.example.Trainogram.dto.PostResponseDto;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.NotEnoughRightsException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post createPost(Long userId, PostRequestDto requestDto)  throws UserNotFoundException, InvalidParamException, IOException;

    void deletePost(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException;

    void updatePost(Long postId, Long userId, PostRequestDto requestDto) throws PostNotFoundException, IOException, UserNotFoundException, InvalidParamException;

    Post createSponsoredPost(Long userId, PostRequestDto requestDto, Long sponsorId) throws UserNotFoundException, InvalidParamException, IOException;

    List<PostResponseDto> showAllUserPosts(Long userId) throws PostNotFoundException;

    List<Post> showRecommendation(Long userId) throws UserNotFoundException;

    FeedbackResponseDto showStatistics(Long userId, Long postId) throws PostNotFoundException, UserNotFoundException, NotEnoughRightsException;

    PostResponseDto showPost(Long postId) throws PostNotFoundException;
}
