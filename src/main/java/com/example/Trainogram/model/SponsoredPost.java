package com.example.Trainogram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sponsored_posts")
@Data
public class SponsoredPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private User sponsor;
}
