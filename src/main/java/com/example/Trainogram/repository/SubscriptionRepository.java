package com.example.Trainogram.repository;

import com.example.Trainogram.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsBySubscribedToIdAndSubscriberId(Long subscribedToId, Long subscriberId);

    Optional<Subscription> findBySubscribedToIdAndSubscriberId(Long subscribedToId, Long subscriberId);

    List<Subscription> findAllBySubscribedToId(Long subscribedToId);

    List<Subscription> findAllBySubscriberId(Long subscriberId);
    int countAllBySubscriberId(Long subscriberId);
    int countAllBySubscribedToId(Long subscribedToId);
}
