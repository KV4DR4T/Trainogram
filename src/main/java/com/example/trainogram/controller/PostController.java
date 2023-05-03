package com.example.trainogram.controller;

import com.example.trainogram.dto.FeedbackResponseDto;
import com.example.trainogram.dto.PostRequestDto;
import com.example.trainogram.dto.PostResponseDto;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.NotEnoughRightsException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Post;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

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



    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, JwtUser jwtUser) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException {
        postService.deletePost(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, JwtUser jwtUser, @ModelAttribute PostRequestDto requestDto) throws UserNotFoundException, InvalidParamException, PostNotFoundException, IOException {
        postService.updatePost(postId, jwtUser.getId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sponsored/{postId}/statistics")
    public ResponseEntity<FeedbackResponseDto> showStatistics(JwtUser jwtUser, @PathVariable Long postId) throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException {
        FeedbackResponseDto feedbackResponseDto = postService.showStatistics(jwtUser.getId(), postId);
        return ResponseEntity.ok(feedbackResponseDto);
    }

    @GetMapping(value = "/recommendations")
    public ResponseEntity<List<Post>> showRecommendation(JwtUser jwtUser) throws UserNotFoundException {
        List<Post> posts = postService.showRecommendation(jwtUser.getId());
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> showAllUserPosts(
            @RequestParam(value = "user id", required = false) Long userId,
            JwtUser jwtUser) throws PostNotFoundException, UserNotFoundException {
        List<PostResponseDto> posts = nonNull(userId) ? postService.showAllUserPosts(userId)
                : postService.showAllUserPosts(jwtUser.getId());
        return ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/{postId}/report")
    public ResponseEntity<?> reportPost(@PathVariable Long postId, JwtUser jwtUser) throws UserNotFoundException, PostNotFoundException {
        reportService.reportPost(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDto> showPost(@PathVariable Long postId) throws PostNotFoundException, UserNotFoundException {
        PostResponseDto postResponseDto = postService.showPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }

}
