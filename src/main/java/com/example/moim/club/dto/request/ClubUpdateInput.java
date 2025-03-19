package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClubUpdateInput {
    private Long id;
    private String title;
    private String explanation;
    private String introduction;
    private String category;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String mainEvent;
    private MultipartFile profileImg;
    private String clubPassword;

    @Builder
    public ClubUpdateInput(Long id, String title, String explanation, String introduction, String category, String university, String gender, String activityArea, String ageRange, String mainEvent, MultipartFile profileImg, String clubPassword) {
        this.id = id;
        this.title = title;
        this.explanation = explanation;
        this.introduction = introduction;
        this.category = category;
        this.university = university;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.mainEvent = mainEvent;
        this.profileImg = profileImg;
        this.clubPassword = clubPassword;
    }
}
