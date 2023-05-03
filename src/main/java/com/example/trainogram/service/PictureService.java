package com.example.trainogram.service;

import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    String uploadImage(List<MultipartFile> files, Long userId, Long postId) throws UserNotFoundException, IOException, InvalidParamException;

    void setAvatar(MultipartFile avatar, Long id, String avatarDir) throws IOException;
}
