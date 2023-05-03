package com.example.trainogram.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class EmailService {
    private final JavaMailSender mailSender;
    private final Queue<SimpleMailMessage> messageQueue = new LinkedBlockingQueue<>();

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("spring.mail.host")
    String from;


    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        messageQueue.add(message);
    }

    @Scheduled(fixedDelay = 1000)
    public void processMessageQueue() {
        SimpleMailMessage message = messageQueue.poll();
        if (message != null) {
            mailSender.send(message);
        }
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(text);
        helper.setSubject(subject);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
        mailSender.send(message);

    }

}
