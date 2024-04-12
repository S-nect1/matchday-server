package com.example.moim.club.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;
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
        try {
            this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource("file:" + club.getProfileImgPath()).getContentAsByteArray());
            this.backgroundImg = Base64.getEncoder().encodeToString(new FileUrlResource("file:" + club.getBackgroundImgPath()).getContentAsByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, List<UserClubOutput> userClubOutputList, List<ScheduleOutput> scheduleOutputs, List<AwardOutput> awardOutputs) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource("file:" + club.getProfileImgPath()).getContentAsByteArray());
            this.backgroundImg = Base64.getEncoder().encodeToString(new FileUrlResource("file:" + club.getBackgroundImgPath()).getContentAsByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.scheduleList = scheduleOutputs;
        this.awardList = awardOutputs;
        this.userClubOutputList = userClubOutputList;
    }
}
