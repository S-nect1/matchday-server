package com.example.moim.notification.dto;

import com.example.moim.club.entity.Schedule;
import com.example.moim.user.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleEncourageEvent {
    private Schedule schedule;
    private List<User> userList;

    public ScheduleEncourageEvent(Schedule schedule, List<User> userList) {
        this.schedule = schedule;
        this.userList = userList;
    }
}
