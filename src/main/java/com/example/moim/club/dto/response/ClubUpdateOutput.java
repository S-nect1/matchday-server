package com.example.moim.club.dto.response;

import com.example.moim.club.entity.Club;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClubUpdateOutput {
    private String title;
    private String explanation;
    private String introduction;
    private String profileImg;
    private String clubCategory;
    private String university;
    private String gender;
    private String activityArea;
    private String sportsType;
    private String ageRange;
    private int memberCount;
    private String mainUniformColor;
    private String subUniformColor;

    private List<UserClubOutput> userList;

    public ClubUpdateOutput(Club club, List<UserClubOutput> userList) {
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.introduction = club.getIntroduction();
        this.profileImg = club.getProfileImgPath();
        this.clubCategory = club.getClubCategory().getKoreanName();
        this.university = club.getUniversity();
        this.gender = club.getGender().getKoreanName();
        this.activityArea = club.getActivityArea().getKoreanName();
        this.sportsType = club.getSportsType().getKoreanName();
        this.ageRange = club.getAgeRange().getKoreanName();
        this.memberCount = club.getMemberCount();
        this.mainUniformColor = club.getMainUniformColor();
        this.subUniformColor = club.getSubUniformColor();
        this.userList = userList;
    }

}
