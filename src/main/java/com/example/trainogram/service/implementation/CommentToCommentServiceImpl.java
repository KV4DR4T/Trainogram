package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToComment;
import com.example.trainogram.model.CommentToPost;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentToCommentRepository;
import com.example.trainogram.service.CommentToCommentService;
import com.example.trainogram.service.CommentToPostService;
import com.example.trainogram.service.NotificationService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentToCommentServiceImpl implements CommentToCommentService {
    private final CommentToPostService commentToPostService;
    private final CommentToCommentRepository commentToCommentRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    public CommentToCommentServiceImpl(CommentToPostService commentToPostService,
                                       CommentToCommentRepository commentToCommentRepository,
                                       UserService userService, NotificationService notificationService) {

        this.commentToPostService = commentToPostService;
        this.commentToCommentRepository = commentToCommentRepository;
        this.userService = userService;
        this.notificationService = notificationService;

    }

    @Override
    public void comment(Long userId, CommentRequestDto requestDto) throws CommentNotFoundException,
            UserNotFoundException, InvalidParamException {
        log.info("Creating comment to comment");
        CommentToPost commentToPost = commentToPostService.getComment(requestDto.getId());

        User user= userService.findById(userId);
        CommentToComment comment = new CommentToComment();
        if (requestDto.getText() == null) {
            throw new InvalidParamException("Comment text can`t be empty");
        } else {
            comment.setText(requestDto.getText());
        }
        comment.setComment(commentToPost);
        comment.setUser(user);
        notificationService.sendNotificationCommentComment(user.getUsername(), commentToPost.getPost().getUser());
        commentToCommentRepository.save(comment);
        log.info("Comment to comment {} was saved", comment);
    }


    @Override
    public void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException {
        log.info("Deleting comment to comment");
        User user = userService.findById(userId);
        CommentToComment comment = commentToCommentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment with id: " + commentId + " not found"));
        if (comment.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
            commentToCommentRepository.delete(comment);
        }
        log.info("Comment to comment {} was deleted", comment);
    }

    @Override
    public List<CommentToComment> getComments(Long commentId) {
        log.info("Getting comments to comment");
        List<CommentToComment> comments = commentToCommentRepository.findAllByCommentId(commentId);
        log.info("Comments to comment got");
        return comments;
    }
}
