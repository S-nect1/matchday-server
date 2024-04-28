package com.example.moim.user.entity;

import com.example.moim.club.entity.UserClub;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.notification.entity.Notifications;
import com.example.moim.user.dto.SignupInput;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
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
    private List<Notifications> notifications = new ArrayList<>();
    
    public static User createUser(SignupInput signupInput) {
        User user = new User();
        user.email = signupInput.getEmail();
        user.password = signupInput.getPassword();
        user.name = signupInput.getName();
        user.birthday = signupInput.getBirthday();
        user.gender = signupInput.getGender();
        user.phone = signupInput.getPhone();
        user.role = Role.USER;
        return user;
    }

    public void setIdAtJWTFilter(Long id, String role) {
        this.id = id;
        this.password = "temppassword";
        this.role = Role.valueOf(role.toUpperCase());
    }
}
