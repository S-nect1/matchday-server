package com.example.moim.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleVoteInput {
    private Long id;
    private String attendance;

    @Builder
    public ScheduleVoteInput(Long id, String attendance) {
        this.id = id;
        this.attendance = attendance;
    }
}
