package com.example.Trainogram.repository;

import com.example.Trainogram.model.ContentType;
import com.example.Trainogram.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture,Long> {
    void deleteAllByContentTypeAndContentId(ContentType contentType, Long contentId);


}
