package com.example.Trainogram.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "pictures")
    private String picturesUrl;
    @Column(name = "text")
    private String text;
    @Column(name = "date")
    @CreatedDate
    private LocalDate date;
    @Column(name = "last_update")
    @LastModifiedDate
    private LocalDate lastUpdate;
}
