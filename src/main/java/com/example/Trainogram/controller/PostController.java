package com.example.Trainogram.controller;

import com.example.Trainogram.dto.FeedbackResponseDto;
import com.example.Trainogram.dto.PostRequestDto;
import com.example.Trainogram.dto.PostResponseDto;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.NotEnoughRightsException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.PostService;
import com.example.Trainogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;
    private final ReportService reportService;

    @Autowired
    public PostController(PostService postService, ReportService reportService) {
        this.postService = postService;
        this.reportService = reportService;
    }

    @PostMapping(value = "/sponsored")
    public ResponseEntity<?> createSponsoredPost(JwtUser jwtUser, @ModelAttribute PostRequestDto requestDto, @RequestParam("sponsor id") Long sponsorId)
            throws UserNotFoundException, InvalidParamException, IOException {
        postService.createSponsoredPost(jwtUser.getId(), requestDto, sponsorId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createPost(JwtUser jwtUser, @ModelAttribute PostRequestDto requestDto) throws UserNotFoundException, InvalidParamException, IOException {
        postService.createPost(jwtUser.getId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{postId}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, JwtUser jwtUser) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException {
        postService.deletePost(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, JwtUser jwtUser, @ModelAttribute PostRequestDto requestDto) throws UserNotFoundException, InvalidParamException, PostNotFoundException, IOException {
        postService.updatePost(postId, jwtUser.getId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sponsored/{postId}/show-statistics")
    public ResponseEntity<FeedbackResponseDto> showStatistics(JwtUser jwtUser, @PathVariable Long postId) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException {
        FeedbackResponseDto feedbackResponseDto = postService.showStatistics(jwtUser.getId(), postId);
        return ResponseEntity.ok(feedbackResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> showPosts(JwtUser jwtUser) throws PostNotFoundException {
        List<PostResponseDto> posts = postService.showAllUserPosts(jwtUser.getId());
        return ResponseEntity.ok(posts);
    }

    @GetMapping(value = "/recommendations")
    public ResponseEntity<List<Post>> showRecommendation(JwtUser jwtUser) throws UserNotFoundException {
        List<Post> posts = postService.showRecommendation(jwtUser.getId());
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> showAllUserPosts(@RequestParam("user id") Long userId) throws PostNotFoundException {
        List<PostResponseDto> posts = postService.showAllUserPosts(userId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/{postId}/report")
    public ResponseEntity<?> reportPost(@PathVariable Long postId, JwtUser jwtUser) throws UserNotFoundException, PostNotFoundException {
        reportService.reportPost(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDto> showPost(@PathVariable Long postId) throws PostNotFoundException {
        PostResponseDto postResponseDto = postService.showPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }

}
