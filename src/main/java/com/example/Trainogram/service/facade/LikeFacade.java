package com.example.Trainogram.service.facade;

import com.example.Trainogram.exception.UserNotFoundException;

public interface LikeFacade  {
    void notificationCommentLike(Long commentId,String username,Long recipientId) throws UserNotFoundException;
    void notificationPostLike(Long postId,String username,Long recipientId) throws UserNotFoundException;
}
