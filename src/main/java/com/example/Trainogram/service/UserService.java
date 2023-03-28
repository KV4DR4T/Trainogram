package com.example.Trainogram.service;

import com.example.Trainogram.dto.*;
import com.example.Trainogram.exception.EmailIsAlreadyTakenException;
import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.Role;
import com.example.Trainogram.model.User;


import java.io.IOException;
import java.util.Map;

public interface UserService {
    void register(UserRegistrationRequestDto requestDto) throws EmailIsAlreadyTakenException, InvalidParamException, IOException;
    UserAuthorizationResponseDto login(UserAuthorizationRequestDto requestDto) throws UserNotFoundException;
    User update(UserUpdateRequestDto requestDto, Long userId) throws InvalidParamException, IOException;
    void deleteUser(Long userId);

    void deleteUserById(Role role, Long userId) throws UserNotFoundException ;
    UserResponseDto showUserById(Long userId) throws UserNotFoundException;


}
