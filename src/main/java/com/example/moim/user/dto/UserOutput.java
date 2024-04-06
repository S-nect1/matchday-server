package com.example.moim.user.dto;

import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class UserOutput {
    private String email;
    private String name;
    private String nickname;
    private String birthday;
    private String gender;
    private String phone;
    private String img;
    private String role;

    public UserOutput(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.birthday = user.getBirthday();
        this.gender = user.getGender().toString();
        this.phone = user.getPhone();
        this.img = user.getImgPath();//base64인코딩 해야함
        this.role = user.getRole().toString();
    }
}
