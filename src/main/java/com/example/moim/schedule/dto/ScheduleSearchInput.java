package com.example.moim.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleSearchInput {
    private final Integer date;
    private final Long clubId;
    private final String search;
    private final String category;

    @Builder
    public ScheduleSearchInput(Integer date, Long clubId, String search, String category) {
        this.date = date;
        this.clubId = clubId;
        this.search = search;
        this.category = category;
    }
}
