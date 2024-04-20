package com.example.moim.club.entity;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String title;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int personnel;//참여인원수
    private int attend;
    private int nonAttend;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    public static Schedule createSchedule(Club club, ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule();
        schedule.club = club;
        schedule.title = scheduleInput.getTitle();
        schedule.location = scheduleInput.getLocation();
        schedule.startTime = scheduleInput.getStartTime();
        schedule.endTime = scheduleInput.getEndTime();
        schedule.personnel = scheduleInput.getPersonnel();
        schedule.attend = 0;
        schedule.nonAttend = 0;
        return schedule;
    }

    public void vote(Boolean attendance) {
        if (attendance) {
            this.attend += 1;
        } else {
            this.nonAttend += 1;
        }
    }
}
