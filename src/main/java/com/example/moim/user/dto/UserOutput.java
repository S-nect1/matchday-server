package com.example.moim.user.dto;

import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class UserOutput {
    private String email;
    private String name;
    private String birthday;
    private String gender;
    private String phone;
    private String img;

    public UserOutput(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().toString();
        this.phone = user.getPhone();
        this.img = user.getImgPath();//base64인코딩 해야함
    }

    public UserOutput(LoginOutput loginOutput) {
        this.email = loginOutput.getEmail();
        this.name = loginOutput.getName();
        this.birthday = loginOutput.getBirthday();
        this.gender = loginOutput.getGender();
        this.phone = loginOutput.getPhone();
        this.img = loginOutput.getImg();//base64인코딩 해야함
    }
}
