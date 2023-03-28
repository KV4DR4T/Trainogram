package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Notification;
import com.example.Trainogram.model.SponsoredPost;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.NotificationRepository;
import com.example.Trainogram.repository.SponsoredPostRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class LikeFacadeImpl implements LikeFacade {
    private final NotificationRepository notificationRepository;
    private final SponsoredPostRepository sponsoredPostRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeFacadeImpl(NotificationRepository notificationRepository, SponsoredPostRepository sponsoredPostRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.sponsoredPostRepository = sponsoredPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void notificationCommentLike(Long commentId,String username,Long recipientId) throws UserNotFoundException {
        User recipient = userRepository.findById(recipientId).orElseThrow(()-> new UserNotFoundException("User with id: "+ recipientId +" not found"));
        Notification notification = new Notification();
        notification.setMessage(username+" liked your comment");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
    }

    @Override
    public void notificationPostLike(Long postId,String username,Long recipientId) throws UserNotFoundException {
        User recipient = userRepository.findById(recipientId).orElseThrow(()-> new UserNotFoundException("User with id: "+ recipientId +" not found"));
        Notification notification = new Notification();
        notification.setMessage(username+" liked your post");
        notification.setRecipient(recipient);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);
        if (sponsoredPostRepository.existsByPostId(postId)){
            SponsoredPost sponsoredPost = sponsoredPostRepository.findByPostId(postId).get();
            Notification notificationToSponsor = new Notification();
            notificationToSponsor.setMessage(username+" liked post you sponsored");
            notificationToSponsor.setRecipient(sponsoredPost.getSponsor());
            notificationToSponsor.setDate(LocalDate.now());
            notificationRepository.save(notificationToSponsor);
        }


    }
}
