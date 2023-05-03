package com.example.trainogram.repository;

import com.example.trainogram.model.ContentType;
import com.example.trainogram.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    void deleteAllByContentTypeAndContentId(ContentType contentType, Long contentId);


}
