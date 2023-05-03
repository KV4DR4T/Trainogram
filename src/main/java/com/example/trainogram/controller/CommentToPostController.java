package com.example.trainogram.controller;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToPost;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.CommentToPostService;
import com.example.trainogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/post-comments")
public class CommentToPostController {
    private final CommentToPostService commentToPostService;
    private final ReportService reportService;

    @Autowired
    public CommentToPostController(CommentToPostService commentToPostService, ReportService reportService) {
        this.commentToPostService = commentToPostService;
        this.reportService = reportService;
    }


    @PostMapping
    public ResponseEntity<?> commentPost(JwtUser jwtUser, @RequestBody CommentRequestDto requestDto)
            throws UserNotFoundException, InvalidParamException, PostNotFoundException {
        commentToPostService.comment(jwtUser.getId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<?> deleteCommentToPost(@PathVariable Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        commentToPostService.deleteComment(jwtUser.getId(), commentId);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<CommentToPost>> getCommentsToPost(@RequestParam("post id") Long postId) {
        List<CommentToPost> comments = commentToPostService.getComments(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping(value = "/{commentId}/report")
    public ResponseEntity<?> reportCommentToPost(@PathVariable Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        reportService.reportCommentToPost(commentId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

}
