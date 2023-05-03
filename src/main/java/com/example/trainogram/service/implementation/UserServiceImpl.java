package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.*;
import com.example.trainogram.exception.EmailIsAlreadyTakenException;
import com.example.trainogram.exception.ErrorCodeException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.repository.SubscriptionRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.security.jwt.JwtTokenProvider;
import com.example.trainogram.service.PictureService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;

@Service
@Slf4j
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
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                           PictureService pictureService, SubscriptionRepository subscriptionRepository,
                           PostRepository postRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.pictureService = pictureService;
        this.subscriptionRepository = subscriptionRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(rollbackFor = {ErrorCodeException.class})
    public void register(UserRegistrationRequestDto requestDto)
            throws EmailIsAlreadyTakenException, InvalidParamException, IOException {
        log.info("Registering user");
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmailIsAlreadyTakenException("Email is taken");
        }

        if (requestDto.getPassword() == null || requestDto.getEmail() == null || requestDto.getUsername() == null) {
            throw new InvalidParamException("Non of the fields can be blank");
        }

        User user = buildUser(requestDto);

        File avatarsDir = initFiles(user);


        if (!requestDto.getAvatar().isEmpty()&&requestDto.getAvatar()!=null) {
            pictureService.setAvatar(requestDto.getAvatar(), user.getId(), avatarsDir.getPath());
        }

        user.setAvatar(avatarsDir.getPath());

    }

    private File initFiles(User user) {
        File userDir = new File(DIR + "/" + user.getId());
        userDir.mkdir();

        File postsDir = new File(DIR + "/" + user.getId() + "/" + "posts");
        postsDir.mkdir();

        File avatarsDir = new File(DIR + "/" + user.getId() + "/" + "avatars");
        avatarsDir.mkdir();
        return avatarsDir;
    }

    private User buildUser(UserRegistrationRequestDto requestDto) {
        log.info("Building user");
        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());

        userRepository.save(user);
        log.info("User {} was saved", user);
        return user;
    }

    @Override
    public UserAuthorizationResponseDto login(UserAuthorizationRequestDto requestDto) throws UserNotFoundException {
        log.info("Logging in");
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        String token = jwtTokenProvider.createToken(email, user.getRole());
        UserAuthorizationResponseDto response = new UserAuthorizationResponseDto();
        response.setToken(token);
        log.info("User {} logged in", user);
        return response;
    }

    @Override
    public User update(UserUpdateRequestDto requestDto, Long userId) throws InvalidParamException, IOException {
        log.info("Updating user");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User with id: " + userId + " not found"));
        if (requestDto.getEmail() == null || requestDto.getUsername() == null) {
            throw new InvalidParamException("Exception");
        }
        user.setEmail(requestDto.getEmail());
        user.setUsername(requestDto.getUsername());

        File avatarDir = new File(DIR + "/" + user.getId() + "/" + "avatars");

        if (!requestDto.getAvatar().isEmpty()) {
            pictureService.setAvatar(requestDto.getAvatar(), user.getId(), avatarDir.getPath());
        }
        log.info("User {} updated", user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user");
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
        log.info("User {} was deleted", user);
    }

    @Override
    @Transactional
    public void deleteUserById(Role role, Long userId) throws UserNotFoundException {
        log.info("Deleting user by id");
        if (role.equals(Role.ADMIN)) {
            User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
            userToDelete.setEmail("DELETED");
            userToDelete.setUsername("DELETED");
            log.info("User {} was deleted", userToDelete);
        }
    }


    @Override
    public UserResponseDto showUserById(Long userId) throws UserNotFoundException {
        log.info("Showing user by id");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setAvatar(user.getAvatar());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setSubscribers(subscriptionRepository.countAllBySubscribedToId(userId));
        userResponseDto.setSubscriptions(subscriptionRepository.countAllBySubscriberId(userId));
        userResponseDto.setPosts(postRepository.countAllByUserId(userId));
        log.info("User {} was shown", user);
        return userResponseDto;
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        log.info("Finding user by email");
        User  user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email: " + email + " not found"));
        log.info("User {} was found", user);
        return user;
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        log.info("Finding user by id");
        User  user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id: " + id + " not found"));
        log.info("User {} was found", user);
        return user;
    }

}


