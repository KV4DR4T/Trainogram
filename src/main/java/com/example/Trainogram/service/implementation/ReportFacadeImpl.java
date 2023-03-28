package com.example.Trainogram.service.implementation;

import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Notification;
import com.example.Trainogram.model.Role;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.NotificationRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.facade.ReportFacade;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class ReportFacadeImpl implements ReportFacade {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public ReportFacadeImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void userReportNotification(Long userId) {
        List<User> admins = userRepository.findAllByRole(Role.ADMIN);
        for (User admin:admins){
            if(!notificationRepository.existsByContentTypeAndContentIdAndRecipientId(ContentType.ACCOUNT,userId, admin.getId())){
                Notification notification = new Notification();
                notification.setMessage("User with id: "+userId+" has been reported multiple times");
                notification.setRecipient(admin);
                notificationRepository.save(notification);
            }
        }
    }
}
