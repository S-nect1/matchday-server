package com.example.moim.user.entity;

import com.example.moim.club.entity.UserClub;
import com.example.moim.notification.entity.Notification;
import com.example.moim.user.dto.JoinDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    private String imgPath;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String refreshToken;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.REMOVE)
    private List<Notification> notifications = new ArrayList<>();
    
    public static User createUser(JoinDTO joinDTO) {
        User user = new User();
        user.email = joinDTO.getEmail();
        user.password = joinDTO.getPassword();
        user.role = Role.USER;
        return user;
    }
}
