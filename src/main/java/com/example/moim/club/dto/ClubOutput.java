package com.example.moim.club.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;

import java.util.List;

@Data
public class ClubOutput {
    private Long id;
    private String title;
    private String explanation;
    private String profileImg;
    private String backgroundImg;
    private List<ScheduleOutput> scheduleList;
    private List<AwardOutput> awardList;
    private List<UserClubOutput> userClubOutputList;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.profileImg = club.getProfileImgPath();//base64 인코딩
        this.backgroundImg = club.getBackgroundImgPath();
    }

    public ClubOutput(Club club, List<UserClubOutput> userClubOutputList, List<ScheduleOutput> scheduleOutputs, List<AwardOutput> awardOutputs) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.profileImg = club.getProfileImgPath();
        this.backgroundImg = club.getBackgroundImgPath();
        this.scheduleList = scheduleOutputs;
        this.awardList = awardOutputs;
        this.userClubOutputList = userClubOutputList;
    }
}
