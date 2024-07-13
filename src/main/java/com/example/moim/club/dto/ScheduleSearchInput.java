package com.example.moim.club.dto;

import lombok.Data;

@Data
public class ScheduleSearchInput {
    private final Integer date;
    private final Long clubId;
    private final String search;
    private final String category;
}
