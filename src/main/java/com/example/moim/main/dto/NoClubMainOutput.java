package com.example.moim.main.dto;

import com.example.moim.club.dto.ScheduleOutput;
import lombok.Data;

import java.util.List;

@Data
public class NoClubMainOutput {
    private List<RecommendClubListOutput> recommendClubList;
    private List<ScheduleOutput> scheduleList;

    public NoClubMainOutput(List<RecommendClubListOutput> recommendClubList, List<ScheduleOutput> scheduleList) {
        this.recommendClubList = recommendClubList;
        this.scheduleList = scheduleList;
    }
}
