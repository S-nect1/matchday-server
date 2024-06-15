package com.example.moim.user.dto;

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
    private Boolean hasClub;

    public UserOutput(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().toString();
        this.phone = user.getPhone();
        if (user.getImgPath() != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(user.getImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
    }
}
