package com.example.Trainogram.service;

import com.example.Trainogram.exception.CommentNotFoundException;
import com.example.Trainogram.exception.PostNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import org.springframework.stereotype.Service;


public interface ReportService {

    void checkUserReports();
    void reportCommentToPost(Long commentId,Long userId) throws UserNotFoundException, CommentNotFoundException;
    void reportCommentToComment(Long commentId,Long userId) throws UserNotFoundException, CommentNotFoundException;
    void reportPost(Long postId,Long userId) throws UserNotFoundException, PostNotFoundException;
    void reportUser(Long userToReportId,Long userId) throws UserNotFoundException;

}
