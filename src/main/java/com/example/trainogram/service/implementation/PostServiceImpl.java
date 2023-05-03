package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.FeedbackResponseDto;
import com.example.trainogram.dto.PostRequestDto;
import com.example.trainogram.dto.PostResponseDto;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.NotEnoughRightsException;
import com.example.trainogram.exception.PostNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.*;
import com.example.trainogram.repository.*;
import com.example.trainogram.service.PictureService;
import com.example.trainogram.service.SubscriptionService;
import com.example.trainogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements com.example.trainogram.service.PostService {
    private final PostRepository postRepository;
    private final PictureService pictureService;
    private final SponsoredPostRepository sponsoredPostRepository;
    private final PictureRepository pictureRepository;
    private final LikeToPostRepository likeToPostRepository;
    private final CommentToPostRepository commentToPostRepository;
    private final CommentToCommentRepository commentToCommentRepository;
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final String DIR = "C:\\Users\\alex\\Documents\\images";

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PictureService pictureService, SponsoredPostRepository sponsoredPostRepository,
                           PictureRepository pictureRepository,
                           LikeToPostRepository likeToPostRepository, CommentToPostRepository commentToPostRepository, CommentToCommentRepository commentToCommentRepository, SubscriptionService subscriptionService, UserRepository userRepository, UserService userService) {
        this.postRepository = postRepository;
        this.pictureService = pictureService;
        this.sponsoredPostRepository = sponsoredPostRepository;
        this.pictureRepository = pictureRepository;
        this.likeToPostRepository = likeToPostRepository;
        this.commentToPostRepository = commentToPostRepository;
        this.commentToCommentRepository = commentToCommentRepository;
        this.subscriptionService = subscriptionService;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public Post createPost(Long userId, PostRequestDto requestDto)
            throws UserNotFoundException, InvalidParamException, IOException {
        log.info("Creating post");
        Post post = new Post();
        if (requestDto.getText() == null) {
            post.setText("");
        } else {
            post.setText(requestDto.getText());
        }
        post.setUser(userRepository.getById(userId));
        post.setLastUpdate(LocalDate.now());
        post.setDate(LocalDate.now());
        postRepository.save(post);
        post.setPicturesUrl(pictureService.uploadImage(requestDto.getFiles(), userId, post.getId()));
        log.info("Post {} was created", post);
        return post;
    }

    @Override
    public void deletePost(Long postId, Long userId)
            throws UserNotFoundException, PostNotFoundException, NotEnoughRightsException {
        log.info("Deleting post");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post with id: " + postId + " not found"));
        if (post.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
            postRepository.delete(post);
            log.info("Post {} was deleted", post);
        } else {
            throw new NotEnoughRightsException("Not enough rights");
        }
    }

    @Override
    @Transactional
    public void updatePost(Long postId, Long userId, PostRequestDto requestDto) throws PostNotFoundException, IOException, UserNotFoundException, InvalidParamException {
        log.info("Updating post");
        Post post = postRepository.findByIdAndUserId(postId, userId).orElseThrow(() -> new PostNotFoundException("User has no posts with id: " + postId));

        if (requestDto.getText() == null) {
            post.setText("");
        } else {
            post.setText(requestDto.getText());
        }

        if (!requestDto.getFiles().isEmpty()) {

            File file = new File(DIR + "/" + userId + "/posts/" + postId);

            FileUtils.forceDelete(file);
            pictureRepository.deleteAllByContentTypeAndContentId(ContentType.POST, postId);
            pictureService.uploadImage(requestDto.getFiles(), userId, postId);
        }
        post.setLastUpdate(LocalDate.now());
        log.info("Post {} was updated", post);
    }

    @Override
    @Transactional
    public Post createSponsoredPost(Long userId, PostRequestDto requestDto, Long sponsorId)
            throws UserNotFoundException, InvalidParamException, IOException {
        log.info("Creating sponsored post");
        Post post = createPost(userId, requestDto);
        User sponsor = userRepository.findById(sponsorId).orElseThrow(() -> new UserNotFoundException("User with id: " + sponsorId + " not found"));
        SponsoredPost sponsoredPost = new SponsoredPost();
        sponsoredPost.setSponsor(sponsor);
        sponsoredPost.setPost(post);
        sponsoredPostRepository.save(sponsoredPost);
        log.info("Sponsored post {} was created", sponsoredPost);
        return post;
    }

    @Override
    public List<PostResponseDto> showAllUserPosts(Long userId) throws PostNotFoundException, UserNotFoundException {
        log.info("Showing all user posts");
        List<Post> posts = postRepository.findAllByUserId(userId);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : posts) {
            postResponseDtos.add(showPost(post.getId()));
        }
        log.info("All user posts were shown");
        return postResponseDtos;
    }


    @Override
    public List<Post> showRecommendation(Long userId) throws UserNotFoundException {
        log.info("Showing recommendation");
        List<Post> posts = Objects.requireNonNull(subscriptionService.subscription(userId,"subscribed" )).stream()
                .flatMap(sub -> postRepository.findAllByUserId(sub.getId()).stream().limit(5)).collect(Collectors.toList());
        log.info("Recommendation was shown");
        return posts;
    }


    @Override
    public FeedbackResponseDto showStatistics(Long userId, Long postId)
            throws PostNotFoundException, UserNotFoundException, NotEnoughRightsException {
        log.info("Showing statistics");
        SponsoredPost sponsoredPost = sponsoredPostRepository.findByPostId(postId).orElseThrow(() -> new PostNotFoundException("Post with id: " + postId + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
        if (sponsoredPost.getSponsor().equals(user) || sponsoredPost.getPost().getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
            FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();
            feedbackResponseDto.setLikes(likeToPostRepository.countAllByPostId(postId));
            List<CommentToPost> comments = commentToPostRepository.findAllByPostId(postId);
            int commentsCount = comments.size();
            for (CommentToPost comment : comments) {
                commentsCount += commentToCommentRepository.countAllByCommentId(comment.getId());
            }
            feedbackResponseDto.setComments(commentsCount);
            log.info("Statistics was shown");
            return feedbackResponseDto;
        } else {
            throw new NotEnoughRightsException("Not enough rights");
        }
    }

    @Override
    public PostResponseDto showPost(Long postId) throws PostNotFoundException, UserNotFoundException {
        log.info("Showing post");
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id: " + postId + " not found"));
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setText(post.getText());
        postResponseDto.setLastUpdate(post.getLastUpdate());
        postResponseDto.setDate(post.getDate());
        postResponseDto.setPicturesUrl(post.getPicturesUrl());
        postResponseDto.setUser(userService.showUserById(post.getUser().getId()));
        postResponseDto.setLikes(likeToPostRepository.countAllByPostId(postId));
        List<CommentToPost> comments = commentToPostRepository.findAllByPostId(postId);
        int commentsCount = comments.size();
        for (CommentToPost comment : comments) {
            commentsCount += commentToCommentRepository.countAllByCommentId(comment.getId());
        }
        postResponseDto.setComments(commentsCount);
        log.info("Post was shown");
        return postResponseDto;
    }


}
