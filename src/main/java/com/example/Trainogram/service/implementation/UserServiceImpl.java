package com.example.Trainogram.service.implementation;

import com.example.Trainogram.dto.*;
import com.example.Trainogram.exception.EmailIsAlreadyTakenException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.*;
import com.example.Trainogram.repository.PostRepository;
import com.example.Trainogram.repository.SubscriptionRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.security.jwt.JwtTokenProvider;
import com.example.Trainogram.service.PictureService;
import com.example.Trainogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PictureService pictureService;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final String DIR = "C:\\Users\\alex\\Documents\\images";


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PictureService pictureService, SubscriptionRepository subscriptionRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.pictureService = pictureService;
        this.subscriptionRepository = subscriptionRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void register(UserRegistrationRequestDto requestDto) throws EmailIsAlreadyTakenException, InvalidParamException, IOException {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailIsAlreadyTakenException("Email is taken");
        }

        if (requestDto.getPassword() == null || requestDto.getEmail() == null || requestDto.getUsername() == null) {
            throw new InvalidParamException("Non of the fields can be blank");
        }

        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());

        userRepository.save(user);

        File userDir = new File(DIR + "/" + user.getId());
        userDir.mkdir();

        File postsDir = new File(DIR + "/" + user.getId() + "/" + "posts");
        postsDir.mkdir();

        File avatarsDir = new File(DIR + "/" + user.getId() + "/" + "avatars");
        avatarsDir.mkdir();

        if(!requestDto.getAvatar().isEmpty()) {
            pictureService.setAvatar(requestDto.getAvatar(), user.getId(), avatarsDir.getPath());
        }

        user.setAvatar(avatarsDir.getPath());
    }

    @Override
    public UserAuthorizationResponseDto login(UserAuthorizationRequestDto requestDto) throws UserNotFoundException {

        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        String token = jwtTokenProvider.createToken(email, user.getRole());
        UserAuthorizationResponseDto response = new UserAuthorizationResponseDto();
        response.setToken(token);
        response.setEmail(email);
        return response;
    }

    @Override
    public User update(UserUpdateRequestDto requestDto, Long userId) throws InvalidParamException, IOException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User with id: " + userId + " not found"));
        if ( requestDto.getEmail() == null || requestDto.getUsername() == null) {
            throw new InvalidParamException("Exception");
        }
        user.setEmail(requestDto.getEmail());
        user.setUsername(requestDto.getUsername());

        File avatarDir = new File(DIR + "/" + user.getId() + "/" + "avatars");

        if (!requestDto.getAvatar().isEmpty()) {
            pictureService.setAvatar(requestDto.getAvatar(), user.getId(), avatarDir.getPath());
        }
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.getById(userId);
//        List<Post> posts = postRepository.findAllByUserId(user.getId()).orElse(new ArrayList<>());
//        if (!posts.isEmpty()) {
//            for (Post post : posts) {
//                postRepository.delete(post);
//                List<Comment> comments = commentRepository.findAllByContentTypeAndContentId(ContentType.POST, post.getId()).get();
//                comments.addAll(comments.stream().flatMap(c -> commentRepository.findAllByContentTypeAndContentId(ContentType.COMMENT, c.getId()).orElse(new ArrayList<>()).stream()).collect(Collectors.toList()));
//                commentRepository.deleteAll(comments);
//            }
//        }
//        userRepository.delete(user);
        user.setUsername("DELETED");
        user.setEmail("DELETED");
    }
    @Override
    @Transactional
    public void deleteUserById(Role role, Long userId) throws UserNotFoundException {
        if(role.equals(Role.ADMIN)) {
            User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
            userToDelete.setEmail("DELETED");
            userToDelete.setUsername("DELETED");
        }
    }


    @Override
    public UserResponseDto showUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+userId+" not found"));
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setAvatar(user.getAvatar());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setSubscribers(subscriptionRepository.countAllBySubscribedToId(userId));
        userResponseDto.setSubscriptions(subscriptionRepository.countAllBySubscriberId(userId));
        userResponseDto.setPosts(postRepository.countAllByUserId(userId));
        return userResponseDto;
    }



}
