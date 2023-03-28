package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.LikeException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.*;
import com.example.Trainogram.service.LikeToCommentService;
import com.example.Trainogram.service.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeToCommentServiceImpl implements LikeToCommentService {
    private final LikeFacade likeFacade;
    private final LikeToCommentRepository likeToCommentRepository;
    private final UserRepository userRepository;
    private final CommentToPostRepository commentRepository;

    @Autowired
    public LikeToCommentServiceImpl(LikeFacade likeFacade, LikeToCommentRepository likeToCommentRepository, UserRepository userRepository, CommentToPostRepository commentRepository) {

        this.likeFacade = likeFacade;
        this.likeToCommentRepository = likeToCommentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void like(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException, LikeException {
        CommentToPost comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        Post post = comment.getPost();
        if (!likeToCommentRepository.existsByUserIdAndCommentId(userId,commentId)) {
            LikeToComment like = new LikeToComment();
            like.setUser(user);
            like.setComment(comment);
            likeToCommentRepository.save(like);
            likeFacade.notificationCommentLike(commentId, user.getUsername(), post.getUser().getId());

        }else {
            throw new LikeException("You can't like this comment twice");
        }
    }

    @Override
    public void unlike(Long commentId, Long userId) throws CommentNotFoundException, UserNotFoundException {
        CommentToPost comment = commentRepository.findById(commentId).orElseThrow(()->new CommentNotFoundException("Comment with id: "+ commentId +" not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        if (likeToCommentRepository.existsByUserIdAndCommentId(user.getId(),comment.getId())) {
            LikeToComment like = likeToCommentRepository.findByUserIdAndCommentId(userId,commentId).get();
            likeToCommentRepository.delete(like);
        }
    }


    @Override
    public List<LikeToComment> getLikes(Long commentId) throws CommentNotFoundException {
        if(likeToCommentRepository.findAllByCommentId(commentId).isEmpty()){
            return new ArrayList<>();
        }else {
            return likeToCommentRepository.findAllByCommentId(commentId);
        }
    }
}
