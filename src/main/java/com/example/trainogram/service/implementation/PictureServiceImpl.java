package com.example.trainogram.service.implementation;

import com.example.trainogram.exception.InvalidParamException;
import com.example.trainogram.exception.UserNotFoundException;
import com.example.trainogram.model.ContentType;
import com.example.trainogram.model.Picture;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.PictureRepository;
import com.example.trainogram.repository.UserRepository;
import com.example.trainogram.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final String FOLDER_PATH = "C:\\Users\\alex\\Documents\\images";
    private final UserRepository userRepository;
//   private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, UserRepository userRepository) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;

    }

    @Override
    public String uploadImage(List<MultipartFile> files, Long userId, Long postId)
            throws UserNotFoundException, IOException, InvalidParamException {
        log.info("Uploading images");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
        File postDir = new File(FOLDER_PATH + "/" + user.getId() + "/" + "posts" + "/" + postId);

        if (!files.isEmpty()) {

            if (!postDir.exists()) {
                postDir.mkdir();
            }

            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID();
                Picture picture = new Picture();
                picture.setName(uuid + file.getOriginalFilename());
                String filePath = postDir.getPath() + "/" + file.getOriginalFilename();
                picture.setUrl(filePath);
                picture.setContentType(ContentType.POST);
                picture.setContentId(postId);
                pictureRepository.save(picture);
                file.transferTo(new File(filePath));
            }

            log.info("Images uploaded to {}", postDir.getPath());
            return postDir.getPath();

        } else {
            throw new InvalidParamException("Post must have at least 1 image");
        }
    }

    @Override
    public void setAvatar(MultipartFile avatar, Long id, String avatarDir) throws IOException {
        log.info("Setting avatar");
        String filePath = avatarDir + "/" + avatar.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        Picture picture = new Picture();
        picture.setUrl(filePath);
        picture.setName(uuid + avatar.getOriginalFilename());
        picture.setContentType(ContentType.ACCOUNT);
        picture.setContentId(id);
        pictureRepository.save(picture);
        avatar.transferTo(new File(filePath));
        log.info("Avatar set to {}", filePath);
    }
}
