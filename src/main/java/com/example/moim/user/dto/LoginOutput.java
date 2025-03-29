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
    private String img;
    private int height;
    private int weight;
    private String mainFoot;
    private String mainPosition;
    private String subPosition;
    private String accessToken;
    private String refreshToken;
    private Boolean hasClub;

    public LoginOutput(User user, String accessToken) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().getKoreanName();
        this.phone = user.getPhone();
        this.activityArea = user.getActivityArea().getKoreanName();
        this.img = user.getImgPath();//base64인코딩 해야함
        this.accessToken = accessToken;
        this.refreshToken = user.getRefreshToken();
    }

    public LoginOutput(User user, String accessToken, Boolean hasClub) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        if (user.getGender() != null) {
            this.gender = user.getGender().getKoreanName();
        }
        this.phone = user.getPhone();
        this.activityArea = user.getActivityArea().getKoreanName();
        this.img = user.getImgPath();//base64인코딩 해야함
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.mainFoot = user.getMainFoot();
        this.mainPosition = user.getMainPosition().name();
        this.subPosition = user.getSubPosition().name();
        this.accessToken = accessToken;
        this.refreshToken = user.getRefreshToken();
        this.hasClub = hasClub;
    }
}
