package com.example.moim.club.entity;

import com.example.moim.club.dto.ScheduleInput;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String title;
    private String contents;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int personnel;//참여인원수

    public static Schedule createSchedule(Club club, ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule();
        schedule.club = club;
        schedule.title = scheduleInput.getTitle();
        schedule.contents = scheduleInput.getContents();
        schedule.location = scheduleInput.getLocation();
        schedule.startTime = scheduleInput.getStartTime();
        schedule.endTime = scheduleInput.getEndTime();
        schedule.personnel = scheduleInput.getPersonnel();
        return schedule;
    }
}
