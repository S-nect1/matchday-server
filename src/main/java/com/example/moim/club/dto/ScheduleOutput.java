package com.example.moim.club.dto;

import com.example.moim.club.entity.Schedule;
import lombok.Data;

@Data
public class ScheduleOutput {
    private Long id;
    private String title;
    private String location;
    private String period;
    private int personnel;
    private String category;
    private String note;

    public ScheduleOutput(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.personnel = schedule.getPersonnel();
        this.category = schedule.getCategory();
        if (schedule.getNote() != null) {
            this.note = schedule.getNote();
        }
    }
}
