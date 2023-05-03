package com.example.trainogram.service.implementation;

import com.example.trainogram.model.Notification;
import com.example.trainogram.model.Report;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.NotificationRepository;
import com.example.trainogram.repository.ReportRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.facade.ReportFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReportFacadeImpl implements ReportFacade {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final NotificationRepository notificationRepository;

    public ReportFacadeImpl(UserRepository userRepository, ReportRepository reportRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void userReportNotification(Long userId) {
        log.info("Creating notification about user report");
        List<User> admins = userRepository.findAllByRole(Role.ADMIN);

        for (User admin : admins) {
          if (reportRepository.findAllByReportedUser(userId).size()>=3) {
                Notification notification = new Notification();
                notification.setMessage("User with id: "+userId+" has been reported multiple times");
                notification.setRecipient(admin);
                notificationRepository.save(notification);
                log.info("Notification about user report {} was created", notification);
            }
        }

    }
}
