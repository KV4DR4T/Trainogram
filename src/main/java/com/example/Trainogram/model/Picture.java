package com.example.Trainogram.model;

import com.example.Trainogram.security.ContentTypeConverter;
import com.example.Trainogram.security.RoleConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
@Data
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "content_type")
    @Convert(converter = ContentTypeConverter.FieldConverter.class)
    private ContentType contentType;
    @Column(name = "content_id")
    private Long contentId;
}
