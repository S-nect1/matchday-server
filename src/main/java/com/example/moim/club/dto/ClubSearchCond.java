package com.example.moim.club.dto;

import lombok.Data;

@Data
public class ClubSearchCond {
    private String category;
    private String search;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String mainEvent;
}
