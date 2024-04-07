package com.example.moim.club.dto;

import com.example.moim.club.entity.UserClub;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserClubOutput {
    private String name;
    private String category;
    private String position;
    private LocalDate joinDate;
    private String birthday;
    private String phone;

    public UserClubOutput(UserClub userClub) {
        this.name = userClub.getUser().getName();
        this.category = userClub.getCategory();
        this.position = userClub.getPosition();
        this.joinDate = userClub.getJoinDate();
        this.birthday = userClub.getUser().getBirthday();
        this.phone = userClub.getUser().getPhone();
    }
}
