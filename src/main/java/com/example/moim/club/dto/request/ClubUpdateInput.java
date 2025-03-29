package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClubUpdateInput {
    /**
     * TODO: id 는 지우기, 찾으려고 필요한 건가? 이건 PathVariable 로 받는게 맞는듯
     */
    /**
     * TODO: 여기 완전 이상한 로직, PATCH로 해놓고 전체 다 바꿔버림;
     */
    private Long id;
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
    public ClubUpdateInput(Long id, String title, String explanation, String introduction, String clubCategory, String organization, String gender, String activityArea, String ageRange, String sportsType, MultipartFile profileImg, String clubPassword) {
        this.id = id;
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
