package com.example.moim.club.dto;

import lombok.Data;

@Data
public class ScheduleSearchInput {
    private Integer date;
    private Long clubId;
    private String search;
    private String category;
}
