package com.example.trainogram.controller;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToComment;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.CommentToCommentService;
import com.example.trainogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment-comments")
public class CommentToCommentController {
    private final ReportService reportService;
    private final CommentToCommentService commentToCommentService;

    @Autowired
    public CommentToCommentController(ReportService reportService, CommentToCommentService commentToCommentService) {
        this.reportService = reportService;
        this.commentToCommentService = commentToCommentService;
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<?> deleteCommentToComment(@PathVariable Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        commentToCommentService.deleteComment(jwtUser.getId(), commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentToComment>> getCommentsToComment(@RequestParam("comment id") Long commentId) {
        List<CommentToComment> comments = commentToCommentService.getComments(commentId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> commentComment(
            JwtUser jwtUser, @RequestBody CommentRequestDto requestDto)
            throws UserNotFoundException, InvalidParamException, CommentNotFoundException {

        commentToCommentService.comment(jwtUser.getId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{commentId}/report")
    public ResponseEntity<?> reportCommentToComment(@PathVariable Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        reportService.reportCommentToComment(commentId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }


}
