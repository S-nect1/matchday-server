package com.example.moim.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
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
}
