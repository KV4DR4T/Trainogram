package com.example.Trainogram.repository;

import com.example.Trainogram.model.Post;
import com.example.Trainogram.model.SponsoredPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SponsoredPostRepository extends JpaRepository<SponsoredPost,Long> {
    Optional<SponsoredPost> findByPostId(Long postId);
    boolean existsByPostId(Long postId);

    Optional<SponsoredPost> findByPostIdAndSponsorId(Long postId,Long sponsorId);
    boolean existsByPostIdAndSponsorId(Long postId,Long sponsorId);
}
