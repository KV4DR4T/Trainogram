package com.example.trainogram.controller;

import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.LikeToComment;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.LikeToCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment-likes")
public class LikeToCommentController {
    private final LikeToCommentService likeToCommentService;

    public LikeToCommentController(LikeToCommentService likeToCommentService) {
        this.likeToCommentService = likeToCommentService;
    }

    @PostMapping
    public ResponseEntity<?> likeComment(@RequestParam("comment id") Long commentId, JwtUser jwtUser) throws UserNotFoundException, LikeException, CommentNotFoundException {
        likeToCommentService.like(commentId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> unlikeComment(@RequestParam("comment id") Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        likeToCommentService.unlike(commentId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<LikeToComment>> getLikesToComment(@RequestParam("comment id") Long commentId) throws CommentNotFoundException {
        List<LikeToComment> likes = likeToCommentService.getLikes(commentId);
        return ResponseEntity.ok(likes);
    }
}
