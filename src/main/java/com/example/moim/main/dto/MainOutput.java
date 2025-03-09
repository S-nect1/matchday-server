package com.example.moim.main.dto;

import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Data
public class MainOutput {
    private Long clubId;
    private String title;
    private String explanation;
    private String profileImg;
    private List<ScheduleOutput> scheduleList;

    public MainOutput(Club club, List<ScheduleOutput> scheduleList) {
        this.clubId = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.scheduleList = scheduleList;
    }
}
