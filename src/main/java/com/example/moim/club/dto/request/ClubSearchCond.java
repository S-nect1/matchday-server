package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubSearchCond {
    private String clubCategory;
    private String search;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String sportsType;

    @Builder
    public ClubSearchCond(String clubCategory, String search, String university, String gender, String activityArea, String ageRange, String sportsType) {
        this.clubCategory = clubCategory;
        this.search = search;
        this.university = university;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.sportsType = sportsType;
    }
}
