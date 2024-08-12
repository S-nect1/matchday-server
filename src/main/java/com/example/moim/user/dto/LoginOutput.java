package com.example.moim.user.dto;

import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class LoginOutput {
    private String email;
    private String name;
    private String birthday;
    private String gender;
    private String phone;
    private String activityArea;
    private String introduction;
    private String img;
    private String accessToken;
    private String refreshToken;
    private Boolean hasClub;

    public LoginOutput(User user, String accessToken) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().toString();
        this.phone = user.getPhone();
        this.activityArea = user.getActivityArea();
        this.introduction = user.getIntroduction();
        this.img = user.getImgPath();//base64인코딩 해야함
        this.accessToken = accessToken;
        this.refreshToken = user.getRefreshToken();
    }

    public LoginOutput(User user, String accessToken, Boolean hasClub) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        if (user.getGender() != null) {
            this.gender = user.getGender().toString();
        }
        this.phone = user.getPhone();
        this.activityArea = user.getActivityArea();
        this.introduction = user.getIntroduction();
        this.img = user.getImgPath();//base64인코딩 해야함
        this.accessToken = accessToken;
        this.refreshToken = user.getRefreshToken();
        this.hasClub = hasClub;
    }
}
