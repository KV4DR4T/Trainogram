package com.example.trainogram.repository;

import com.example.trainogram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(Long userId);

    Optional<Post> findByIdAndUserId(Long postId, Long userId);

    int countAllByUserId(Long userId);


}
