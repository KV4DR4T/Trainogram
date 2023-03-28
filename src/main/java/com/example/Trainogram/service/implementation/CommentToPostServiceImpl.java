package com.example.Trainogram.service.implementation;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.*;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.*;
import com.example.Trainogram.service.CommentToPostService;
import com.example.Trainogram.service.facade.CommentsFacade;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CommentToPostServiceImpl implements CommentToPostService {
    private final PostRepository postRepository;
    private final CommentsFacade commentsFacade;
    private final CommentToPostRepository commentToPostRepository;
    private final UserRepository userRepository;




    public CommentToPostServiceImpl(PostRepository postRepository, CommentsFacade commentsFacade, CommentToPostRepository commentToPostRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentsFacade = commentsFacade;
        this.commentToPostRepository = commentToPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void comment(Long userId, Long postId, CommentRequestDto requestDto) throws PostNotFoundException, UserNotFoundException, InvalidParamException {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("Post with id: "+ postId+" not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        CommentToPost comment = new CommentToPost();
        if(requestDto.getText()==null){
            throw new InvalidParamException("Comment text can`t be empty");
        }else {
            comment.setText(requestDto.getText());
        }
        comment.setUser(user);
        commentsFacade.sendNotification(user.getUsername(),post.getUser(),postId);
        commentToPostRepository.save(comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) throws UserNotFoundException, CommentNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        CommentToPost comment =commentToPostRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        if(comment.getUser().equals(user)||user.getRole().equals(Role.ADMIN)){
            commentToPostRepository.delete(comment);
        }
    }
    @Override
    public List<CommentToPost> getComments(Long postId) {
        return commentToPostRepository.findAllByPostId(postId);
    }

}
