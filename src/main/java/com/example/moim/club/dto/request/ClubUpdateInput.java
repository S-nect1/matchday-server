package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClubUpdateInput {

    private String title;
    private String explanation;
    private String introduction;
    private String clubCategory;
    private String organization;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String sportsType;
    private MultipartFile profileImg;
    private String clubPassword;

    @Builder
    public ClubUpdateInput(String title, String explanation, String introduction, String clubCategory, String organization, String gender, String activityArea, String ageRange, String sportsType, MultipartFile profileImg, String clubPassword) {
        this.title = title;
        this.explanation = explanation;
        this.introduction = introduction;
        this.clubCategory = clubCategory;
        this.organization = organization;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.sportsType = sportsType;
        this.profileImg = profileImg;
        this.clubPassword = clubPassword;
    }
}
