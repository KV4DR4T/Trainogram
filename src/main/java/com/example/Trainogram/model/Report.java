package com.example.Trainogram.model;

import com.example.Trainogram.security.ContentTypeConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;


}
