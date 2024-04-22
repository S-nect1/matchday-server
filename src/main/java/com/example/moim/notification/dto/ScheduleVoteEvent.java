package com.example.moim.notification.dto;

import com.example.moim.club.entity.Schedule;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class ScheduleVoteEvent {
    private Schedule schedule;
    private User user;

    public ScheduleVoteEvent(Schedule schedule, User user) {
        this.schedule = schedule;
        this.user = user;
    }
}
