package com.example.moim.club.dto;

import com.example.moim.club.entity.Schedule;
import lombok.Data;

@Data
public class ScheduleDetailOutput {
    private Long id;
    private String title;
    private String location;
    private String period;
    private int attend;
    private int nonAttend;

    public ScheduleDetailOutput(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
    }
}
