package com.example.trainogram.repository;

import com.example.trainogram.model.SponsoredPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SponsoredPostRepository extends JpaRepository<SponsoredPost, Long> {
    Optional<SponsoredPost> findByPostId(Long postId);

    boolean existsByPostId(Long postId);

    Optional<SponsoredPost> findByPostIdAndSponsorId(Long postId, Long sponsorId);

    boolean existsByPostIdAndSponsorId(Long postId, Long sponsorId);
}
