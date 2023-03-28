package com.example.Trainogram.service;

import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.Picture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    String uploadImage(List<MultipartFile> files, Long userId,Long postId) throws UserNotFoundException, IOException, InvalidParamException;
    void setAvatar(MultipartFile avatar,Long id, String avatarDir) throws IOException;
}
