package com.example.trainogram.security;

import com.example.trainogram.model.User;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.security.jwt.JwtUser;
import com.example.trainogram.security.jwt.JwtUserFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email:" + email + " not found"));
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
