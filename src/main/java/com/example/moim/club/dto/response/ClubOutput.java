package com.example.moim.club.dto.response;

import com.example.moim.club.entity.*;
import com.example.moim.global.enums.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class ClubOutput {
    private ClubRole clubRole;
    private Long id;
    private String title;
    private String explanation;
    private String introduction;
    private String profileImg;
    private String clubCategory;
    private String organization;
    private String gender;
    private String activityArea;
    private String sportsType;
    private String ageRange;
    private int memberCount;
    private String mainUniformColor;
    private String subUniformColor;

    private List<UserClubOutput> userList;
//    private List<ScheduleOutput> scheduleList;
//    private List<AwardOutput> awardList;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.mainUniformColor = club.getMainUniformColor();
        this.subUniformColor = club.getSubUniformColor();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, ClubRole clubRole) {
        this.clubRole = clubRole;
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.mainUniformColor = club.getMainUniformColor();
        this.subUniformColor = club.getSubUniformColor();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, List<UserClubOutput> userList, ClubRole clubRole) {
        this.clubRole = clubRole;
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.introduction = club.getIntroduction();
        this.clubCategory = club.getClubCategory().getKoreanName();
        this.organization = club.getUniversity();
        this.gender = club.getGender().getKoreanName();
        this.activityArea = club.getActivityArea().getKoreanName();
        this.sportsType = club.getSportsType().getKoreanName();
        this.ageRange = club.getAgeRange().getKoreanName();
        this.mainUniformColor = club.getMainUniformColor();
        this.subUniformColor = club.getSubUniformColor();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        this.scheduleList = scheduleOutputs;
//        this.awardList = awardOutputs;
        this.userList = userList;
        this.memberCount = club.getMemberCount();
    }
}
