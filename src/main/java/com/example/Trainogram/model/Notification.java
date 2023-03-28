package com.example.Trainogram.model;

import com.example.Trainogram.security.ContentTypeConverter;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
    @Column(name = "date")
    @CreatedDate
    private LocalDate date;
}
