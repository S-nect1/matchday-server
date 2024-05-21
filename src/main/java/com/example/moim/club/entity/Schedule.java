package com.example.moim.club.entity;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleUpdateInput;
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
    private String category;
    private String note;
    private int attend;
    private int nonAttend;
    private int undecided;

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
        schedule.category = scheduleInput.getCategory();
        if (scheduleInput.getNote() != null) {
            schedule.note = scheduleInput.getNote();
        }
        schedule.attend = 0;
        schedule.nonAttend = 0;
        schedule.undecided = 0;
        return schedule;
    }

    public void vote(String attendance) {
        if (attendance.equals("attend")) {
            this.attend += 1;
        } else if (attendance.equals("absent")) {
            this.nonAttend += 1;
        } else {
            this.undecided += 1;
        }
    }

    public void reVote(String originalAttendance, String attendance) {
        if (originalAttendance.equals("attend")) {
            this.attend -= 1;
        } else if (originalAttendance.equals("absent")) {
            this.nonAttend -= 1;
        } else {
            this.undecided -= 1;
        }
        if (attendance.equals("attend")) {
            this.attend += 1;
        } else if (attendance.equals("absent")) {
            this.nonAttend += 1;
        } else {
            this.undecided += 1;
        }
    }

    public void updateSchedule(ScheduleUpdateInput scheduleUpdateInput) {
        this.title = scheduleUpdateInput.getTitle();
        this.location = scheduleUpdateInput.getLocation();
        this.startTime = scheduleUpdateInput.getStartTime();
        this.endTime = scheduleUpdateInput.getEndTime();
        this.personnel = scheduleUpdateInput.getPersonnel();
        this.category = scheduleUpdateInput.getCategory();
        if (scheduleUpdateInput.getNote() != null) {
            this.note = scheduleUpdateInput.getNote();
        }
    }
}
