package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.CommentRequestDto;
import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.CommentToPost;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.CommentToPostRepository;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.CommentToPostService;
import com.example.trainogram.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CommentToPostServiceImpl implements CommentToPostService {
    private final PostRepository postRepository;
    private final NotificationService notificationService;
    private final CommentToPostRepository commentToPostRepository;
    private final UserRepository userRepository;


    public CommentToPostServiceImpl(PostRepository postRepository,
                                    NotificationService notificationService,
                                    CommentToPostRepository commentToPostRepository,
                                    UserRepository userRepository) {

        this.postRepository = postRepository;
        this.notificationService = notificationService;
        this.commentToPostRepository = commentToPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void comment(Long userId,  CommentRequestDto requestDto) throws PostNotFoundException,
            UserNotFoundException, InvalidParamException {
        log.info("Creating comment to post");
        Post post = postRepository.findById(requestDto.getId()).orElseThrow(() ->
                new PostNotFoundException("Post with id: " + requestDto.getId() + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));
        CommentToPost comment = new CommentToPost();
        if (requestDto.getText() == null) {
            throw new InvalidParamException("Comment text can`t be empty");
        } else {
            comment.setText(requestDto.getText());
        }
        comment.setUser(user);
        comment.setPost(post);
        notificationService.sendNotificationCommentPost(user.getUsername(), post.getUser(), requestDto.getId());
        commentToPostRepository.save(comment);
        log.info("Comment to post {} created", comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) throws UserNotFoundException, CommentNotFoundException {
        log.info("Deleting comment to post");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
        CommentToPost comment = commentToPostRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment with id: " + commentId + " not found"));
        if (comment.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
            commentToPostRepository.delete(comment);
        }
        log.info("Comment to post {} deleted", comment);
    }

    @Override
    public List<CommentToPost> getComments(Long postId) {
        log.info("Getting comments to post");
        List<CommentToPost> commentsToPost=commentToPostRepository.findAllByPostId(postId);
        log.info("Comments to post got");
        return commentsToPost;
    }

    @Override
    public CommentToPost getComment(Long commentId) throws CommentNotFoundException {
        log.info("Getting comment to post");
        CommentToPost commentToPost=commentToPostRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id: " + commentId + " not found"));
        log.info("Comment to post got");
        return commentToPost;
    }

}
