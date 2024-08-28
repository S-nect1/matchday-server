package com.example.moim.match.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;

@Data
public class MatchClubOutput {
    private Long clubId;
    private String title;
    private String activityArea;
    private String ageRange;
    private String gender;

    public MatchClubOutput(Club club) {
        this.clubId = club.getId();
        this.title = club.getTitle();
        this.activityArea = club.getActivityArea();
        this.ageRange = club.getAgeRange();
        this.gender = club.getGender();
    }
}

