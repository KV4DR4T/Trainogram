package com.example.trainogram.service;

import com.example.trainogram.dto.*;
import com.example.trainogram.exception.EmailIsAlreadyTakenException;
import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;

import java.io.IOException;

public interface UserService {
    void register(UserRegistrationRequestDto requestDto) throws EmailIsAlreadyTakenException, InvalidParamException, IOException;

    UserAuthorizationResponseDto login(UserAuthorizationRequestDto requestDto) throws UserNotFoundException;

    User update(UserUpdateRequestDto requestDto, Long userId) throws InvalidParamException, IOException;

    void deleteUser(Long userId);

    void deleteUserById(Role role, Long userId) throws UserNotFoundException;

    UserResponseDto showUserById(Long userId) throws UserNotFoundException;
    User findByEmail(String email) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

}
