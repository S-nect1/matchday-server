package com.example.moim.club.dto;

import com.example.moim.club.entity.Schedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleOutput {
    private Long id;
    private String title;
    private String location;
    private LocalDate date;
    private String period;
    private int minPeople;
    private String category;
    private String note;

    public ScheduleOutput(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.location = schedule.getLocation();
        this.date = schedule.getStartTime().toLocalDate();
        this.period = schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.minPeople = schedule.getMinPeople();
        this.category = schedule.getCategory();
        if (schedule.getNote() != null) {
            this.note = schedule.getNote();
        }
    }
}
