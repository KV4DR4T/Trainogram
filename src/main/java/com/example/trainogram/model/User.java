package com.example.trainogram.model;

import com.example.trainogram.security.RoleConverter;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
//    @Builder.Default
    @Column(name = "role")
    @Convert(converter = RoleConverter.FieldConverter.class)
    private Role role = Role.USER;

}
