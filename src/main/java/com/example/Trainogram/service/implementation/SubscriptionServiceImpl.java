package com.example.Trainogram.service.implementation;

import com.example.Trainogram.dto.UserResponseDto;
import com.example.Trainogram.exception.SubscriptionNotFoundException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.Subscription;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.SubscriptionRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.service.SubscriptionService;
import com.example.Trainogram.service.UserService;
import com.example.Trainogram.service.facade.SubscriptionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionFacade subscriptionFacade;
    private final UserService userService;

    @Autowired
    public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, SubscriptionFacade subscriptionFacade,  UserService userService) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionFacade = subscriptionFacade;
        this.userService = userService;
    }
    @Override
    @Transactional
    public void subscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException {
        User subscriber = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        User subscribedTo = userRepository.findById(subscribeToId).orElseThrow(()-> new UserNotFoundException("User with id: "+ subscribeToId +" not found"));
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setSubscribedTo(subscribedTo);
        if(!subscriptionRepository.existsBySubscribedToIdAndSubscriberId(subscribeToId,subscriber.getId())||subscribeToId.equals(subscriber.getId())) {
            subscriptionRepository.save(subscription);
            subscriptionFacade.sendNotification(subscribeToId, subscriber.getUsername(), subscriber.getId());
        }else{
            throw new SubscriptionNotFoundException("You are already subscribed to user with email: "+subscribedTo.getEmail());
        }
    }

    @Override
    @Transactional
    public void unsubscribe(Long userId, Long subscribeToId) throws UserNotFoundException, SubscriptionNotFoundException {
        User subscriber = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+ userId +" not found"));
        User subscribedTo = userRepository.findById(subscribeToId).orElseThrow(()-> new UserNotFoundException("User with id: "+ subscribeToId +" not found"));
        if(subscriptionRepository.existsBySubscribedToIdAndSubscriberId(subscribedTo.getId(),subscriber.getId())||!subscribedTo.getId().equals(subscriber.getId())) {
            Subscription subscription = subscriptionRepository.findBySubscribedToIdAndSubscriberId(subscribeToId,subscriber.getId()).orElseThrow(()-> new SubscriptionNotFoundException("You are not subscribed to user with email: "+ subscribedTo.getEmail()));
            subscriptionRepository.delete(subscription);
        }
    }

    @Override
    public List<User> subscribers(Long userId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscribedToId(userId);
        List<User> users = new ArrayList<>();
        if(subscriptions.isEmpty()){
            return users;
        }
        for (Subscription sub: subscriptions){
            users.add(sub.getSubscriber());
        }

        return users;
    }

    @Override
    public List<UserResponseDto> subscribed(Long userId) throws UserNotFoundException {

        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberId(userId);
        List<UserResponseDto> users = new ArrayList<>();
        if(subscriptions.isEmpty()){
            return users;
        }
        for(Subscription sub:subscriptions){
            users.add(userService.showUserById(sub.getSubscribedTo().getId()));
        }
        return users;
    }
}
