package com.example.trainogram.repository;

import com.example.trainogram.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsBySubscribedToIdAndSubscriberId(Long subscribedToId, Long subscriberId);

    Optional<Subscription> findBySubscribedToIdAndSubscriberId(Long subscribedToId, Long subscriberId);

    List<Subscription> findAllBySubscribedToId(Long subscribedToId);

    List<Subscription> findAllBySubscriberId(Long subscriberId);

    int countAllBySubscriberId(Long subscriberId);

    int countAllBySubscribedToId(Long subscribedToId);
}
