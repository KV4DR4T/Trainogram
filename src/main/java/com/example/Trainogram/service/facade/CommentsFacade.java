package com.example.Trainogram.service.facade;

import com.example.Trainogram.model.User;

public interface CommentsFacade {
    void sendNotification(String commentatorUsername, User recipient, Long postId) ;
}
