package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.NotificationRepository;
import com.example.Trainogram.repository.SponsoredPostRepository;
import com.example.Trainogram.service.facade.CommentsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentFacadeImpl implements CommentsFacade {

    private final NotificationRepository notificationRepository;
    private final SponsoredPostRepository sponsoredPostRepository;

    @Autowired
    public CommentFacadeImpl(NotificationRepository notificationRepository, SponsoredPostRepository sponsoredPostRepository) {
        this.notificationRepository = notificationRepository;
        this.sponsoredPostRepository = sponsoredPostRepository;
    }

    public void sendNotification(String commentatorUsername,User recipient,Long postId) {
        Notification notification = new Notification();
        notification.setMessage(commentatorUsername+ " commented your post");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        if (sponsoredPostRepository.existsByPostId(postId)){
            SponsoredPost sponsoredPost = sponsoredPostRepository.findByPostId(postId).get();
            Notification notificationToSponsor = new Notification();
            notificationToSponsor.setMessage(commentatorUsername+" commented post you sponsored");
            notificationToSponsor.setRecipient(sponsoredPost.getSponsor());
            notificationToSponsor.setDate(LocalDate.now());
            notificationRepository.save(notificationToSponsor);
        }
    }
}
