package com.example.trainogram.controller;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.LikeToPost;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.LikeToPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post-likes")
public class LikeToPostController {
    private final LikeToPostService likeToPostService;

    public LikeToPostController(LikeToPostService likeToPostService) {
        this.likeToPostService = likeToPostService;
    }

    @PostMapping
    public ResponseEntity<?> likePost(@RequestParam("post id") Long postId, JwtUser jwtUser) throws UserNotFoundException, PostNotFoundException, LikeException {
        likeToPostService.like(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> unlikePost(@RequestParam("post id") Long postId, JwtUser jwtUser) throws PostNotFoundException {
        likeToPostService.unlike(postId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LikeToPost>> getLikesToPost(@RequestParam("post id") Long postId) throws PostNotFoundException {
        List<LikeToPost> likes = likeToPostService.getLikes(postId);
        return ResponseEntity.ok(likes);
    }
}
