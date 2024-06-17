package com.example.moim.user.dto;

import com.example.moim.club.entity.UserClub;
import lombok.Data;

@Data
public class MyClubOutput {
    private Long clubId;
    private String clubName;
    private String explanation;

    public MyClubOutput(UserClub userClub) {
        this.clubId = userClub.getClub().getId();
        this.clubName = userClub.getClub().getTitle();
    }
}
