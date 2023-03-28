package com.example.Trainogram.service.implementation;

import com.example.Trainogram.dto.CommentRequestDto;
import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.CommentToComment;
import com.example.Trainogram.model.CommentToPost;
import com.example.Trainogram.model.Role;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.CommentToCommentRepository;
import com.example.Trainogram.repository.CommentToPostRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.CommentToCommentService;
import com.example.Trainogram.service.facade.CommentsFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentToCommentServiceImpl implements CommentToCommentService {
    private final CommentToPostRepository commentToPostRepository;
    private final CommentToCommentRepository commentToCommentRepository;
    private final UserRepository userRepository;
    private final CommentsFacade commentsFacade;

    public CommentToCommentServiceImpl(CommentToPostRepository commentToPostRepository, CommentToCommentRepository commentToCommentRepository, UserRepository userRepository, CommentsFacade commentsFacade) {
        this.commentToPostRepository = commentToPostRepository;
        this.commentToCommentRepository = commentToCommentRepository;
        this.userRepository = userRepository;
        this.commentsFacade = commentsFacade;
    }

    @Override
    public void comment(Long userId, Long commentId, CommentRequestDto requestDto) throws CommentNotFoundException, UserNotFoundException, InvalidParamException {
        CommentToPost commentToPost = commentToPostRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        User user= userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        CommentToComment comment = new CommentToComment();
        if(requestDto.getText()==null){
            throw new InvalidParamException("Comment text can`t be empty");
        }else {
            comment.setText(requestDto.getText());
        }
        comment.setComment(commentToPost);
        comment.setUser(user);
        commentsFacade.sendNotification(user.getUsername(),commentToPost.getPost().getUser(),commentToPost.getPost().getId());
        commentToCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        CommentToComment comment = commentToCommentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        if(comment.getUser().equals(user)||user.getRole().equals(Role.ADMIN)){
            commentToCommentRepository.delete(comment);
        }
    }

    @Override
    public List<CommentToComment> getComments(Long commentId) {
        if(commentToCommentRepository.existsById(commentId)) {
            return commentToCommentRepository.findAllByCommentId(commentId);
        }else {
            return new ArrayList<>();
        }
    }
}
