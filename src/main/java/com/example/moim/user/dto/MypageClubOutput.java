package com.example.moim.user.dto;

import com.example.moim.club.entity.UserClub;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class MypageClubOutput {
    private Long userClubId;
    private String clubName;
    private String activityDays;
    private String scheduleRate;
    private String matchRate;

    public MypageClubOutput(UserClub userClub, String scheduleRate, String matchRate) {
        this.userClubId = userClub.getId();
        this.clubName = userClub.getClub().getTitle();
        this.activityDays = String.valueOf(userClub.getJoinDate().until(LocalDate.now(), ChronoUnit.DAYS));
        this.scheduleRate = scheduleRate;
        this.matchRate = matchRate;
    }
}
