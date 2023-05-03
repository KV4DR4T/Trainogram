package com.example.trainogram.service;

import com.example.trainogram.exception.CommentNotFoundException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;


public interface ReportService {

    void checkUserReports();

    void reportCommentToPost(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException;

    void reportCommentToComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException;

    void reportPost(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException;

    void reportUser(Long userToReportId, Long userId) throws UserNotFoundException;

}
