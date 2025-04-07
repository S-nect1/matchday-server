package com.example.moim.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClubInput {

    @NotBlank(message = "모임 이름을 적어야합니다!")
    private String title;
    @NotBlank(message = "모임 설명을 적어야합니다!")
    private String explanation;
    @NotBlank(message = "모임 소개를 적어야합니다!")
    private String introduction;
    @NotBlank(message = "클럽 카테고리를 지정해야합니다!")
    private String clubCategory;
    private String university;
    @NotBlank(message = "성별을 지정해야합니다!")
    private String gender;
    @NotBlank(message = "활동 지역을 지정해야합니다!")
    private String activityArea;
    @NotBlank(message = "연령대를 지정해야합니다!")
    private String ageRange;
    @NotBlank(message = "스포츠 종목을 지정해야합니다!")
    private String sportsType;
    @NotBlank(message = "모임 비밀번호를 적어야합니다!")
    private String clubPassword;
    private MultipartFile profileImg;
    @NotBlank(message = "메인 유니폼 색을 지정해야합니다!")
    private String mainUniformColor;
    @NotBlank(message = "서브 유니폼 색을 지정해야합니다!")
    private String subUniformColor;

    @Builder
    public ClubInput(String title, String explanation, String introduction, String clubCategory, String university, String gender, String activityArea, String ageRange, String sportsType, String clubPassword, MultipartFile profileImg, String mainUniformColor, String subUniformColor) {
        this.title = title;
        this.explanation = explanation;
        this.introduction = introduction;
        this.clubCategory = clubCategory;
        this.university = university;
        this.gender = gender;
        this.activityArea = activityArea;
        this.ageRange = ageRange;
        this.sportsType = sportsType;
        this.clubPassword = clubPassword;
        this.profileImg = profileImg;
        this.mainUniformColor = mainUniformColor;
        this.subUniformColor = subUniformColor;
    }
}
