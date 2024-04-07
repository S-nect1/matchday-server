package com.example.moim.club.dto;

import com.example.moim.club.entity.Award;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class ClubOutput {
    private Long id;
    private String title;
    private String explanation;
    private String profileImg;
    private String backgroundImg;
    private List<Schedule> scheduleList;
    private List<Award> awardList;
    private List<UserClubOutput> userClubOutputList;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.profileImg = club.getProfileImgPath();//base64 인코딩
        this.backgroundImg = club.getBackgroundImgPath();
    }

    public ClubOutput(Club club, List<UserClubOutput> userClubOutputList) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.profileImg = club.getProfileImgPath();
        this.backgroundImg = club.getBackgroundImgPath();
//        this.scheduleList = scheduleList;
//        this.awardList = awardList;
        this.userClubOutputList = userClubOutputList;
    }
}
