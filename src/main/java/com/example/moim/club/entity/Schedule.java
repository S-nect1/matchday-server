package com.example.moim.club.entity;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleUpdateInput;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.match.entity.Match;
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
    private int minPeople;
    private String category;
    private String note;
    private int attend;
    private int nonAttend;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    @OneToOne(mappedBy = "schedule", cascade =  CascadeType.REMOVE)
    private Match match;

    public static Schedule createSchedule(Club club, ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule();
        schedule.club = club;
        schedule.title = scheduleInput.getTitle();
        schedule.location = scheduleInput.getLocation();
        schedule.startTime = scheduleInput.getStartTime();
        schedule.endTime = scheduleInput.getEndTime();
        schedule.minPeople = scheduleInput.getMinPeople();
        schedule.category = scheduleInput.getCategory();
        if (scheduleInput.getNote() != null) {
            schedule.note = scheduleInput.getNote();
        }
        schedule.attend = 0;
        schedule.nonAttend = 0;
        return schedule;
    }

    public void vote(String attendance) {
        if (attendance.equals("attend")) {
            this.attend += 1;
        } else if (attendance.equals("absent")) {
            this.nonAttend += 1;
        }
    }

    public void reVote(String originalAttendance, String attendance) {
        if (originalAttendance.equals("attend")) {
            this.attend -= 1;
        } else if (originalAttendance.equals("absent")) {
            this.nonAttend -= 1;
        }
        if (attendance.equals("attend")) {
            this.attend += 1;
        } else if (attendance.equals("absent")) {
            this.nonAttend += 1;
        }
    }

    public void updateSchedule(ScheduleUpdateInput scheduleUpdateInput) {
        this.title = scheduleUpdateInput.getTitle();
        this.location = scheduleUpdateInput.getLocation();
        this.startTime = scheduleUpdateInput.getStartTime();
        this.endTime = scheduleUpdateInput.getEndTime();
        this.minPeople = scheduleUpdateInput.getMinPeople();
        this.category = scheduleUpdateInput.getCategory();
        if (scheduleUpdateInput.getNote() != null) {
            this.note = scheduleUpdateInput.getNote();
        }
    }
}
