package com.example.moim.schedule.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleUpdateInput;
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
    private int minPeople;
    private String category;
    private String note;
    private int attend;
    private int nonAttend;
    private Boolean isClose;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

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
        schedule.isClose = false;
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
        if (scheduleUpdateInput.getTitle() != null) {
            this.title = scheduleUpdateInput.getTitle();
        }
        if (scheduleUpdateInput.getLocation() != null) {
            this.location = scheduleUpdateInput.getLocation();
        }
        if (scheduleUpdateInput.getStartTime() != null) {
            this.startTime = scheduleUpdateInput.getStartTime();
        }
        if (scheduleUpdateInput.getEndTime() != null) {
            this.endTime = scheduleUpdateInput.getEndTime();
        }
        if (scheduleUpdateInput.getMinPeople() != null) {
            this.minPeople = scheduleUpdateInput.getMinPeople();
        }
        if (scheduleUpdateInput.getCategory() != null) {
            this.category = scheduleUpdateInput.getCategory();
        }
        if (scheduleUpdateInput.getNote() != null) {
            this.note = scheduleUpdateInput.getNote();
        }
    }

    public void closeSchedule() {
        this.isClose = true;
    }

}
