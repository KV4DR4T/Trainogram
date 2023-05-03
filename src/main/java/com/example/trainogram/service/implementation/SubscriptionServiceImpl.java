package com.example.trainogram.service.implementation;

import com.example.trainogram.dto.UserResponseDto;
import com.example.trainogram.exception.SubscriptionNotFoundException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Subscription;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.repository.SubscriptionRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.SubscriptionService;
import com.example.trainogram.service.UserService;
import com.example.trainogram.service.facade.SubscriptionFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionFacade subscriptionFacade;
    private final UserService userService;
    private final PostRepository postRepository;

    @Autowired
    public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository,
                                   SubscriptionFacade subscriptionFacade, UserService userService,
                                   PostRepository postRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionFacade = subscriptionFacade;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void subscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException {
        log.info("Subscribing");
        User subscriber = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));
        User subscribedTo = userRepository.findById(subscribeToId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + subscribeToId + " not found"));
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setSubscribedTo(subscribedTo);
        if (!subscriptionRepository.existsBySubscribedToIdAndSubscriberId(subscribeToId,
                subscriber.getId()) || subscribeToId.equals(subscriber.getId())) {
            subscriptionRepository.save(subscription);
            log.info("Subscription {} was created", subscription);
            subscriptionFacade.sendNotification(subscribeToId, subscriber.getUsername(), subscriber.getId());
        } else {
            throw new SubscriptionNotFoundException("You are already subscribed to user with email: " + subscribedTo.getEmail());
        }
    }

    @Override
    @Transactional
    public void unsubscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException {
        log.info("Unsubscribing");
        User subscriber = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userId + " not found"));
        User subscribedTo = userRepository.findById(subscribeToId).orElseThrow(() ->
                new UserNotFoundException("User with id: " + subscribeToId + " not found"));
        if (subscriptionRepository.existsBySubscribedToIdAndSubscriberId(subscribedTo.getId(),
                subscriber.getId()) || !subscribedTo.getId().equals(subscriber.getId())) {
            Subscription subscription = subscriptionRepository.findBySubscribedToIdAndSubscriberId(subscribeToId, subscriber.getId()).orElseThrow(() -> new SubscriptionNotFoundException("You are not subscribed to user with email: " + subscribedTo.getEmail()));
            subscriptionRepository.delete(subscription);
            log.info("Subscription {} was deleted", subscription);
        }
    }

    @Override
    public List<UserResponseDto> subscription(Long userId, String subscriptionRequest) throws UserNotFoundException {
        log.info("Getting subscriptions");
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscribedToId(userId);
        List<UserResponseDto> users = new ArrayList<>();

        if (subscriptions.isEmpty()) {
            return users;
        }
        if (subscriptionRequest.equals("subscribers")) {
            for (Subscription sub : subscriptions) {
                users.add(userService.showUserById(sub.getSubscriber().getId()));
            }
            return users;
        }else if (subscriptionRequest.equals("subscribed")){
            for (Subscription sub : subscriptions) {
                users.add(userService.showUserById(sub.getSubscribedTo().getId()));
            }
            return users;
        }
        for (Subscription sub : subscriptions) {
            users.add(userService.showUserById(sub.getSubscriber().getId()));
        }
        log.info("Subscriptions {} got", subscriptions);
        return users;
    }

}
