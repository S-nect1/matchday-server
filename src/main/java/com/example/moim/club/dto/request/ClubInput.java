package com.example.moim.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClubInput {

    @NotBlank(message = "모임 이름을 적어야합니다!")
    private String title;
    @NotBlank(message = "모임 설명을 적어야합니다!")
    private String explanation;
    @NotBlank(message = "모임 소개를 적어야합니다!")
    private String introduction;
    private String category;
    private String university;
    private String gender;
    private String activityArea;
    private String ageRange;
    private String mainEvent;
    private String clubPassword;
    private MultipartFile profileImg;
    private String mainUniformColor;
    private String subUniformColor;

    @Builder
    public ClubInput(String title, String explanation, String introduction, String category, String university, String gender, String activityArea, String ageRange, String mainEvent, String clubPassword, MultipartFile profileImg, String mainUniformColor, String subUniformColor) {
        this.title = title;
        this.explanation = explanation;
        this.introduction = introduction;
        this.category = category;
        this.university = university;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.mainEvent = mainEvent;
        this.clubPassword = clubPassword;
        this.profileImg = profileImg;
        this.mainUniformColor = mainUniformColor;
        this.subUniformColor = subUniformColor;
    }
}
