package com.example.moim.club.dto;

import com.example.moim.club.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleDetailOutput {
    private Long id;
    private String title;
    private String location;
    private String period;
    private int personnel;//참여인원수
    private String category;
    private int attend;
    private int nonAttend;
    private List<CommentOutput> commentOutputList;

    public ScheduleDetailOutput(Schedule schedule, List<CommentOutput> commentOutputList) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.personnel = schedule.getPersonnel();
        this.category = schedule.getCategory();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
        this.commentOutputList = commentOutputList;
    }
}
