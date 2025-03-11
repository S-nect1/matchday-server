package com.example.moim.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubSearchCond {
    private String category;
    private String search;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String mainEvent;
}
