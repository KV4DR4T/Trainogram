package com.example.Trainogram.security.jwt;

import com.example.Trainogram.model.User;

import java.util.List;

public final class JwtUserFactory {
    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getEmail(),
                user.getPassword(), user.getAvatar(),
                user.getRole(), List.of(user.getRole()));
    }

}
