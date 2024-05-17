package com.example.moim.user.entity;

import com.example.moim.club.entity.UserClub;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.notification.entity.Notifications;
import com.example.moim.user.dto.GoogleUserSignup;
import com.example.moim.user.dto.KakaoUserSignup;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.dto.SocialSignupInput;
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
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    private String imgPath;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String fcmToken;
    
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

    public static User createGoogleUser(GoogleUserSignup googleUserSignup) {
        User user = new User();
        user.email = googleUserSignup.getEmailAddresses().get(0).get("value").toString();
//        user.name = googleUserSignup.getNames().get(0).get("displayName").toString();
//        user.birthday = googleUserSignup.getBirthdays().get(0).get("date").toString().replaceAll("[^0-9 ]", "").replace(' ', '.');
        user.gender = Gender.from(googleUserSignup.getGenders().get(0).get("value").toString());
//        user.phone = googleUserSignup.getPhoneNumbers().get(0).get("value").toString().replace("-","");
        user.role = Role.USER;
        return user;
    }

    public static User createKakaoUser(KakaoUserSignup kakaoUserSignup) {
        User user = new User();
        user.email = kakaoUserSignup.getEmail();
        user.gender = Gender.from(kakaoUserSignup.getGender());
        user.role = Role.USER;
        return user;
    }

    public void fillUserInfo(SocialSignupInput socialSignupInput) {
        this.name = socialSignupInput.getName();
        this.birthday = socialSignupInput.getBirthday();
        this.phone = socialSignupInput.getPhone();
        if (socialSignupInput.getFcmToken() != null) {
            this.fcmToken = socialSignupInput.getFcmToken();
        }
    }

    public void setIdAtJWTFilter(Long id, String role) {
        this.id = id;
        this.password = "temppassword";
        this.role = Role.valueOf(role.toUpperCase());
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
