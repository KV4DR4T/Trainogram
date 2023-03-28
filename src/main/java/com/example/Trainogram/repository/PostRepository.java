package com.example.Trainogram.repository;

import com.example.Trainogram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByUserId(Long userId);
    Optional<Post> findByIdAndUserId(Long postId, Long userId);
    int countAllByUserId(Long userId);


}
