package com.example.Trainogram.controller;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.CommentToComment;
import com.example.Trainogram.security.jwt.JwtUser;
import com.example.Trainogram.service.CommentToCommentService;
import com.example.Trainogram.service.ReportService;
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
    public ResponseEntity<?> commentComment(@RequestParam("comment id") Long commentId,
                                            JwtUser jwtUser, @RequestBody CommentRequestDto requestDto)
                                            throws UserNotFoundException, InvalidParamException, CommentNotFoundException {

        commentToCommentService.comment(jwtUser.getId(), commentId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{commentId}/report")
    public ResponseEntity<?> reportCommentToComment(@PathVariable Long commentId, JwtUser jwtUser) throws UserNotFoundException, CommentNotFoundException {
        reportService.reportCommentToComment(commentId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }


}
