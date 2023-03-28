package com.example.Trainogram.controller;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.CommentToPost;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.CommentToPostService;
import com.example.Trainogram.service.ReportService;
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
    public ResponseEntity<?> commentPost(JwtUser jwtUser, @RequestBody CommentRequestDto requestDto, @RequestParam("post id") Long postId) throws UserNotFoundException, InvalidParamException, PostNotFoundException {
        commentToPostService.comment(jwtUser.getId(), postId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<?> deleteCommentToPost(@PathVariable Long commentId,JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        commentToPostService.deleteComment(jwtUser.getId(),commentId);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<CommentToPost>> getCommentsToPost(@RequestParam("post id") Long postId){
        List<CommentToPost> comments = commentToPostService.getComments(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping(value = "/{commentId}/report")
    public ResponseEntity<?> reportCommentToPost(@PathVariable Long commentId,JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        reportService.reportCommentToPost(commentId,jwtUser.getId());
        return ResponseEntity.ok().build();
    }

}
