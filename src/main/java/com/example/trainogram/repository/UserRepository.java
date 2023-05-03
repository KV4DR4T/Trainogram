package com.example.trainogram.repository;

import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);

    @Query(value = "SELECT * FROM users u WHERE u.reports>=3", nativeQuery = true)
    List<User> findAllReported();

}
