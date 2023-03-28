package com.example.Trainogram.repository;

import com.example.Trainogram.model.Role;
import com.example.Trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
    @Query(value = "SELECT * FROM users u WHERE u.reports>=3",nativeQuery = true)
    List<User> findAllReported();

}
