package com.example.trainogram.service.implementation;

import com.example.trainogram.exception.LikeException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.LikeToPost;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.LikeToPostRepository;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.LikeToPostService;
import com.example.trainogram.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LikeToPostServiceImpl implements LikeToPostService {
    private final PostRepository postRepository;
    private final NotificationService notificationService;
    private final LikeToPostRepository likeToPostRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeToPostServiceImpl(PostRepository postRepository, NotificationService notificationService, LikeToPostRepository likeToPostRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.notificationService = notificationService;
        this.likeToPostRepository = likeToPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void like(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException, LikeException {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post with id: " + postId + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));

        if (!likeToPostRepository.existsByUserIdAndPostId(userId, postId)) {
            LikeToPost like = new LikeToPost();
            like.setUser(user);
            like.setPost(post);
            likeToPostRepository.save(like);
            notificationService.notificationPostLike(postId, user.getUsername(), post.getUser().getId());
        } else {
            throw new LikeException("You can't like this post twice");
        }
    }

    @Override
    public void unlike(Long postId, Long userId) throws PostNotFoundException {
        log.info("Deleting like to post");
        LikeToPost like = likeToPostRepository.findByUserIdAndPostId(userId, postId).orElseThrow(() ->
                new PostNotFoundException("Like with postId: " + postId + " and userId: " + userId + " not found"));
        likeToPostRepository.delete(like);
        log.info("Like to post {} was deleted", like);
    }

    @Override
    public List<LikeToPost> getLikes(Long postId) {
        log.info("Getting likes to post");
        List<LikeToPost> likesToPost = likeToPostRepository.findAllByPostId(postId);
        log.info("Likes to post got");
        return likesToPost;

    }
}
