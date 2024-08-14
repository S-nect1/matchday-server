package com.example.moim.club.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Data
public class ClubOutput {
    private String userCategoryInClub;
    private Long id;
    private String title;
    private String explanation;
    private String introduction;
    private String profileImg;
    private String category;
    private String university;
    private String gender;
    private String activityArea;
    private String mainEvent;
    private String ageRange;
//    private List<ScheduleOutput> scheduleList;
//    private List<AwardOutput> awardList;
    private List<UserClubOutput> userList;
    private int memberCount;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, String category) {
        this.userCategoryInClub = category;
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, List<UserClubOutput> userList, String category) {
        this.userCategoryInClub = category;
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.introduction = club.getIntroduction();
        this.category = club.getCategory();
        this.university = club.getUniversity();
        this.gender = club.getGender();
        this.activityArea = club.getActivityArea();
        this.mainEvent = club.getMainEvent();
        this.ageRange = club.getAgeRange();
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
