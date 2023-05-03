package com.example.trainogram.service.implementation;

import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToPost;
import com.example.trainogram.model.LikeToComment;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentToPostRepository;
import com.example.trainogram.repository.LikeToCommentRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.LikeToCommentService;
import com.example.trainogram.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LikeToCommentServiceImpl implements LikeToCommentService {

    private final NotificationService notificationService;
    private final LikeToCommentRepository likeToCommentRepository;
    private final UserRepository userRepository;
    private final CommentToPostRepository commentRepository;

    @Autowired
    public LikeToCommentServiceImpl(NotificationService notificationService, LikeToCommentRepository likeToCommentRepository,
                                    UserRepository userRepository, CommentToPostRepository commentRepository) {
        this.notificationService = notificationService;
        this.likeToCommentRepository = likeToCommentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void like(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException, LikeException {
        log.info("Creating like to comment");
        CommentToPost comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id: " + commentId + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));
        Post post = comment.getPost();
        if (!likeToCommentRepository.existsByUserIdAndCommentId(userId, commentId)) {
            LikeToComment like = new LikeToComment();
            like.setUser(user);
            like.setComment(comment);
            likeToCommentRepository.save(like);
            notificationService.notificationCommentLike(commentId, user.getUsername(), post.getUser().getId());
            log.info("Like to comment {} created", like);
        } else {
            throw new LikeException("You can't like this comment twice");
        }
    }

    @Override
    public void unlike(Long commentId, Long userId) throws CommentNotFoundException {
        log.info("Deleting like to comment");
        LikeToComment like = likeToCommentRepository.findByUserIdAndCommentId(userId, commentId).orElseThrow(() ->
                new CommentNotFoundException("Like with user id: " + userId + " and comment id: " + commentId + " not found"));
        likeToCommentRepository.delete(like);
        log.info("Like to comment {} deleted", like);
    }


    @Override
    public List<LikeToComment> getLikes(Long commentId) {
        log.info("Getting likes to comment");
        List<LikeToComment> likesToComment = likeToCommentRepository.findAllByCommentId(commentId);
        log.info("Likes to comment got");
        return likesToComment;
    }
}