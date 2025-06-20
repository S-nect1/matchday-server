package com.example.moim.notification.dto;

import com.example.moim.schedule.entity.Schedule;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class ScheduleUpdateEvent {
    private Schedule schedule;
    private User user;

    public ScheduleUpdateEvent(Schedule schedule, User user) {
        this.schedule = schedule;
        this.user = user;
    }
}
