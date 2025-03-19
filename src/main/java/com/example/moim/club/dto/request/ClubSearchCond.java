package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClubSearchCond {
    private String category;
    private String search;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String mainEvent;

    @Builder
    public ClubSearchCond(String category, String search, String university, String gender, String activityArea, String ageRange, String mainEvent) {
        this.category = category;
        this.search = search;
        this.university = university;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.mainEvent = mainEvent;
    }
}
