package com.example.moim.club.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleInput {
    private Long clubId;
    private String title;
    private String contents;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int personnel;//참여인원수
}
