package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.LikeException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.LikeToPostRepository;
import com.example.Trainogram.repository.PostRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.LikeToPostService;
import com.example.Trainogram.service.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeToPostServiceImpl implements LikeToPostService {
    private final PostRepository postRepository;
    private final LikeFacade likeFacade;
    private final LikeToPostRepository likeToPostRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeToPostServiceImpl(PostRepository postRepository, LikeFacade likeFacade, LikeToPostRepository likeToPostRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.likeFacade = likeFacade;
        this.likeToPostRepository = likeToPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void like(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException, LikeException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id: "+ postId +" not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));

        if (!likeToPostRepository.existsByUserIdAndPostId(userId,postId)) {
            LikeToPost like = new LikeToPost();
            like.setUser(user);
            like.setPost(post);
            likeToPostRepository.save(like);
            likeFacade.notificationPostLike(postId, user.getUsername(), post.getUser().getId());
        }else {
            throw new LikeException("You can't like this post twice");
        }
    }

    @Override
    public void unlike(Long postId, Long userId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id: "+ postId +" not found"));
        if (likeToPostRepository.existsByUserIdAndPostId(userId,post.getId())) {
            LikeToPost like = likeToPostRepository.findByUserIdAndPostId(userId,postId).get();
            likeToPostRepository.delete(like);
        }
    }
    @Override
    public List<LikeToPost> getLikes(Long postId)  {
        if(likeToPostRepository.findAllByPostId(postId).isEmpty()){
            return new ArrayList<>();
        }else {
            return likeToPostRepository.findAllByPostId(postId);
        }
    }
}
