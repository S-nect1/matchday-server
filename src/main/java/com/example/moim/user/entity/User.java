package com.example.moim.user.entity;

import com.example.moim.club.entity.UserClub;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.global.enums.ActivityArea;
import com.example.moim.global.enums.Gender;
import com.example.moim.global.enums.Position;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.user.dto.GoogleUserSignup;
import com.example.moim.user.dto.KakaoUserSignup;
import com.example.moim.user.dto.NaverUserSignup;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.dto.SocialSignupInput;
import com.example.moim.user.dto.UserUpdateInput;
import com.example.moim.user.exceptions.advice.UserControllerAdvice;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
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
    @Enumerated(EnumType.STRING)
    private ActivityArea activityArea;
    private int height;
    private int weight;
    private String mainFoot;
    @Enumerated(EnumType.STRING)
    private Position mainPosition;
    @Enumerated(EnumType.STRING)
    private Position subPosition;
    @Column(unique = true)
    private String refreshToken;
    private String fcmToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.REMOVE)
    private List<NotificationEntity> notifications = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String birthday, Gender gender, String phone,
                String imgPath,
                Role role, ActivityArea activityArea, int height, int weight, String mainFoot, Position mainPosition,
                Position subPosition, String refreshToken, String fcmToken, List<UserClub> userClub,
                List<NotificationEntity> notifications) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.imgPath = imgPath;
        this.role = role;
        this.activityArea = activityArea;
        this.height = height;
        this.weight = weight;
        this.mainFoot = mainFoot;
        this.mainPosition = mainPosition;
        this.subPosition = subPosition;
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
        this.userClub = userClub;
        this.notifications = notifications;
    }

    public static User createUser(SignupInput signupInput) {
        User user = new User();
        user.email = signupInput.getEmail();
        user.password = signupInput.getPassword();
        user.name = signupInput.getName();
        user.birthday = signupInput.getBirthday();
        user.gender = Gender.fromKoreanName(signupInput.getGender())
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.INVALID_GENDER));
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
//            user.gender = Gender.from(googleUserSignup.getGenders().get(0).get("value").toString());
            /**
             * FIXME: 구글에서 어떤 값으로 넘어오는지 몰라서, 추후에 고쳐야 함
             */
            user.gender = Gender.valueOf(googleUserSignup.getGenders().get(0).get("value").toString());
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
        user.gender = Gender.fromKoreanName(naverUserSignup.getGender())
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.INVALID_GENDER));
        user.role = Role.USER;
        return user;
    }

    public void fillUserInfo(SocialSignupInput socialSignupInput, String imgPath) {
        this.name = socialSignupInput.getName();
        this.birthday = socialSignupInput.getBirthday();
        this.phone = socialSignupInput.getPhone();
        this.imgPath = imgPath;
//        this.gender = Gender.from(socialSignupInput.getGender());
        this.gender = Gender.fromKoreanName(socialSignupInput.getGender())
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.INVALID_GENDER));
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
            this.gender = Gender.fromKoreanName(userUpdateInput.getGender())
                    .orElseThrow(() -> new UserControllerAdvice(ResponseCode.INVALID_GENDER));
        }
        if (userUpdateInput.getActivityArea() != null && !userUpdateInput.getActivityArea().name().isBlank()) {
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
