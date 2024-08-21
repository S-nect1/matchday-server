package com.example.moim.club.dto;

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
}
