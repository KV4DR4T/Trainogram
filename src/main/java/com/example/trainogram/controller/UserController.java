package com.example.trainogram.controller;

import com.example.trainogram.dto.ChangePasswordRequestDto;
import com.example.trainogram.dto.UserResponseDto;
import com.example.trainogram.dto.UserUpdateRequestDto;
import com.example.trainogram.email.EmailService;
import com.example.trainogram.email.SendGridEmailService;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.User;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.service.LikeToPostService;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.ReportService;
import com.example.trainogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    public final PostService postService;
    public final LikeToPostService likeToPostService;
    private final UserService userService;
    private final ReportService reportService;
    private final EmailService emailService;
    private final SendGridEmailService sendGridEmailService;

    @Autowired
    public UserController(PostService postService, LikeToPostService likeService, UserService userService, ReportService reportService, EmailService emailService, SendGridEmailService sendGridEmailService) {
        this.postService = postService;
        this.likeToPostService = likeService;
        this.userService = userService;
        this.reportService = reportService;
        this.emailService = emailService;
        this.sendGridEmailService = sendGridEmailService;
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(JwtUser jwtUser, @ModelAttribute UserUpdateRequestDto requestDto) throws Exception {
        User user = userService.update(requestDto, jwtUser.getId());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(JwtUser jwtUser) {
        userService.deleteUser(jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{userId}/report")
    public ResponseEntity<?> reportUser(JwtUser jwtUser, @PathVariable Long userId) throws UserNotFoundException {
        reportService.reportUser(userId, jwtUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUserById(JwtUser jwtUser, @PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUserById(jwtUser.getRole(), userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResponseDto> showUserById(@PathVariable Long userId) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.showUserById(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponseDto> showUser(JwtUser jwtUser) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.showUserById(jwtUser.getId());
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping(value = "/password")
    public ResponseEntity<?> requestToChangePassword(@RequestBody String email) {
        sendGridEmailService.sendMail(email);
        //emailService.sendMessage("kondralex222@gmail.com","change password","Changing password to "+ password+"?");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/validate")
    public ResponseEntity<?> validate(@ModelAttribute ChangePasswordRequestDto changePasswordRequestDto, JwtUser jwtUser) {
        sendGridEmailService.validate(changePasswordRequestDto, jwtUser.getEmail());
        return ResponseEntity.ok().build();
    }
}
