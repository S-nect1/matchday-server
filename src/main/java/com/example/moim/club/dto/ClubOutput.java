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
    private String introduction;
    private String profileImg;
    private String backgroundImg;
    private List<ScheduleOutput> scheduleList;
    private List<AwardOutput> awardList;
    private List<UserClubOutput> userList;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
            if (club.getBackgroundImgPath() != null) {
                this.backgroundImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getBackgroundImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClubOutput(Club club, List<UserClubOutput> userList, List<ScheduleOutput> scheduleOutputs, List<AwardOutput> awardOutputs) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
            if (club.getBackgroundImgPath() != null) {
                this.backgroundImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getBackgroundImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.scheduleList = scheduleOutputs;
        this.awardList = awardOutputs;
        this.userList = userList;
    }
}
