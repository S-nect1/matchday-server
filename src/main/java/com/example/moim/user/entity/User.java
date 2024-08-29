package com.example.moim.user.entity;

import com.example.moim.club.entity.UserClub;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.notification.entity.Notifications;
import com.example.moim.user.dto.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
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
    private String activityArea;
    private int height;
    private int weight;
    private String mainFoot;
    private String mainPosition;
    private String subPosition;
    @Column(unique = true)
    private String refreshToken;
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
        if (!googleUserSignup.getGenders().isEmpty()) {
            user.gender = Gender.from(googleUserSignup.getGenders().get(0).get("value").toString());
        }
//        user.phone = googleUserSignup.getPhoneNumbers().get(0).get("value").toString().replace("-","");
        user.role = Role.USER;
        return user;
    }

    public static User createKakaoUser(KakaoUserSignup kakaoUserSignup) {
        User user = new User();
        user.email = kakaoUserSignup.getEmail();
        user.role = Role.USER;
        return user;
    }

    public static User createNaverUser(NaverUserSignup naverUserSignup) {
        User user = new User();
        user.email = naverUserSignup.getEmail();
        user.gender = Gender.from(naverUserSignup.getGender());
        user.role = Role.USER;
        return user;
    }

    public void fillUserInfo(SocialSignupInput socialSignupInput, String imgPath) {
        this.name = socialSignupInput.getName();
        this.birthday = socialSignupInput.getBirthday();
        this.phone = socialSignupInput.getPhone();
        this.imgPath = imgPath;
        this.gender = Gender.from(socialSignupInput.getGender());
        this.activityArea = socialSignupInput.getActivityArea();
        this.height = socialSignupInput.getHeight();
        this.weight = socialSignupInput.getWeight();
        this.mainFoot = socialSignupInput.getMainFoot();
        this.mainPosition = socialSignupInput.getMainPosition();
        this.subPosition = socialSignupInput.getSubPosition();
        if (socialSignupInput.getFcmToken() != null) {
            this.fcmToken = socialSignupInput.getFcmToken();
        }
    }

    public void updateUserInfo(UserUpdateInput userUpdateInput, String imgPath) {
        if (userUpdateInput.getName() != null && !userUpdateInput.getName().isBlank()) {
            this.name = userUpdateInput.getName();
        }
        if (userUpdateInput.getBirthday() != null && !userUpdateInput.getBirthday().isBlank()) {
            this.birthday = userUpdateInput.getBirthday();
        }
        if (userUpdateInput.getPhone() != null && !userUpdateInput.getPhone().isBlank()) {
            this.phone = userUpdateInput.getPhone();
        }
        if (imgPath != null) {
            if (this.imgPath != null) {
                new File(this.imgPath).delete();
            }
            this.imgPath = imgPath;
        }
        if (userUpdateInput.getGender() != null && !userUpdateInput.getGender().isBlank()) {
            this.gender = Gender.from(userUpdateInput.getGender());
        }
        if (userUpdateInput.getActivityArea() != null && !userUpdateInput.getActivityArea().isBlank()) {
            this.activityArea = userUpdateInput.getActivityArea();
        }
        if (userUpdateInput.getHeight() != null) {
            this.height = userUpdateInput.getHeight();
        }
        if (userUpdateInput.getWeight() != null) {
            this.weight = userUpdateInput.getWeight();
        }
        if (userUpdateInput.getMainFoot() != null) {
            this.mainFoot = userUpdateInput.getMainFoot();
        }
        if (userUpdateInput.getMainPosition() != null) {
            this.mainPosition = userUpdateInput.getMainPosition();
        }
        if (userUpdateInput.getSubPosition() != null) {
            this.subPosition = userUpdateInput.getSubPosition();
        }
        if (userUpdateInput.getFcmToken() != null) {
            this.fcmToken = userUpdateInput.getFcmToken();
        }
    }

    public void setIdAtJWTFilter(Long id, String role) {
        this.id = id;
        this.password = "temppassword";
        this.role = Role.valueOf(role.toUpperCase());
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
