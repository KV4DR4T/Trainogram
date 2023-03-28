package com.example.Trainogram.service.implementation;

import com.example.Trainogram.exception.InvalidParamException;
import com.example.Trainogram.exception.UserNotFoundException;
import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Picture;
import com.example.Trainogram.model.User;
import com.example.Trainogram.repository.PictureRepository;
import com.example.Trainogram.repository.UserRepository;
import com.example.Trainogram.security.jwt.JwtTokenProvider;
import com.example.Trainogram.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final String FOLDER_PATH="C:\\Users\\alex\\Documents\\images";
   private final UserRepository userRepository;
   private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,  UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String uploadImage(List<MultipartFile> files,Long userId,Long postId) throws UserNotFoundException, IOException, InvalidParamException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id: "+userId+" not found"));
        File postDir = new File(FOLDER_PATH +"/"+user.getId()+ "/"+"posts"+"/"+postId);

        if(!files.isEmpty()) {

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

            return postDir.getPath();

        }else {
            throw new InvalidParamException("Post must have at least 1 image");
        }
    }

    @Override
    public void setAvatar(MultipartFile avatar, Long id, String avatarDir) throws IOException {
        String filePath = avatarDir+"/"+avatar.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        Picture picture = new Picture();
        picture.setUrl(filePath);
        picture.setName(uuid+avatar.getOriginalFilename());
        picture.setContentType(ContentType.ACCOUNT);
        picture.setContentId(id);
        pictureRepository.save(picture);
        avatar.transferTo(new File(filePath));

    }
}
