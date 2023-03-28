package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.*;
import com.example.Trainogram.service.ReportService;
import com.example.Trainogram.service.facade.ReportFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final CommentToCommentRepository commentToCommentRepository;
    private final CommentToPostRepository commentToPostRepository;
    private final ReportRepository reportRepository;
    private final ReportFacade reportFacade;
    private final PostRepository postRepository;

    @Autowired
    public ReportServiceImpl(UserRepository userRepository, CommentToCommentRepository commentToCommentRepository, CommentToPostRepository commentToPostRepository, ReportRepository reportRepository, ReportFacade reportFacade, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentToCommentRepository = commentToCommentRepository;
        this.commentToPostRepository = commentToPostRepository;
        this.reportRepository = reportRepository;
        this.reportFacade = reportFacade;
        this.postRepository = postRepository;
    }




    @Scheduled(fixedRate = 4000)
    @Async
    public void checkUserReports(){
        List<User> users = userRepository.findAllReported();
        for(User u: users){
            reportFacade.userReportNotification(u.getId());
        }
    }

    @Override
    @Transactional
    public void reportPost(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException {
        Report report = new Report();
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("Post with id: "+postId+" not found"));
        User reportedUser = post.getUser();
        report.setUser(user);
        report.setReportedUser(reportedUser);

        reportRepository.save(report);
    }

    @Override
    public void reportUser(Long userToReportId, Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        User reportedUser = userRepository.findById(userToReportId).orElseThrow(() -> new UserNotFoundException("User with id: "+userId+" not found"));
        Report report = new Report();
        report.setUser(user);
        report.setReportedUser(reportedUser);
        reportRepository.save(report);
    }



    @Override
    public void reportCommentToPost(Long commentId,Long userId) throws UserNotFoundException, CommentNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with email: "+userId+" not found"));
        CommentToPost comment = commentToPostRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        Report report = new Report();
        report.setReportedUser(comment.getUser());
        report.setUser(user);
        reportRepository.save(report);
    }

    @Override
    public void reportCommentToComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with email: "+userId+" not found"));
        CommentToComment comment = commentToCommentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+commentId+" not found"));
        Report report = new Report();
        report.setUser(user);
        report.setReportedUser(comment.getUser());
        reportRepository.save(report);
    }


}
