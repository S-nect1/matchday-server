package com.example.moim.schedule.dto;

import com.example.moim.match.entity.MatchApplication;
import lombok.Data;

@Data
public class ScheduleMatchApplyClubOutput {
    private String title;

    public ScheduleMatchApplyClubOutput(MatchApplication matchApplication) {
        this.title = matchApplication.getClub().getTitle();
    }
}


