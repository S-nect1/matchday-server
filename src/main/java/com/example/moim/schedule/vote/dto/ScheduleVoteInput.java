package com.example.moim.schedule.vote.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleVoteInput {
    private String attendance;

    @Builder
    public ScheduleVoteInput(String attendance) {
        this.attendance = attendance;
    }
}
