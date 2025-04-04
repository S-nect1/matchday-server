package com.example.moim.user.dto;

import com.example.moim.global.enums.Gender;
import com.example.moim.user.entity.User;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class UserOutput {
    private String email;
    private String name;
    private String birthday;
    private String gender;
    private String phone;
    private String img;
    private String activityArea;
    private int height;
    private int weight;
    private String mainFoot;
    private String mainPosition;
    private String subPosition;
    private Boolean hasClub;

    public UserOutput(User user, Boolean hasClub) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().getKoreanName();
        this.phone = user.getPhone();
        this.hasClub = hasClub;
        if (user.getImgPath() != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(user.getImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.activityArea = user.getActivityArea().getKoreanName();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.mainFoot = user.getMainFoot();
        this.mainPosition = user.getMainPosition().name();
        this.subPosition = user.getSubPosition().name();
    }

    public UserOutput(LoginOutput loginOutput) {
        this.email = loginOutput.getEmail();
        this.name = loginOutput.getName();
        this.birthday = loginOutput.getBirthday();
        this.gender = loginOutput.getGender();
        this.phone = loginOutput.getPhone();
        this.hasClub = loginOutput.getHasClub();
        if (loginOutput.getImg() != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(loginOutput.getImg()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.activityArea = loginOutput.getActivityArea();
        this.height = loginOutput.getHeight();
        this.weight = loginOutput.getWeight();
        this.mainFoot = loginOutput.getMainFoot();
        this.mainPosition = loginOutput.getMainPosition();
        this.subPosition = loginOutput.getSubPosition();
    }
}
