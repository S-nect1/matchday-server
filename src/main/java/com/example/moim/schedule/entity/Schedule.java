package com.example.moim.schedule.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleUpdateInput;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.vote.entity.AttendanceType;
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
    @Enumerated(value = EnumType.STRING)
    private ScheduleCategory category;
    private String note;
    private int attend;
    private int nonAttend;
    private Boolean isClose;
    private int viewCount;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    public static Schedule from(Club club, ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule();
        schedule.club = club;
        schedule.title = scheduleInput.getTitle();
        schedule.location = scheduleInput.getLocation();
        schedule.startTime = scheduleInput.getStartTime();
        schedule.endTime = scheduleInput.getEndTime();
        schedule.minPeople = scheduleInput.getMinPeople();
        schedule.category = ScheduleCategory.fromKoreanName(scheduleInput.getCategory()).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.INVALID_SCHEDULE_CATEGORY));
        if (scheduleInput.getNote() != null) {
            schedule.note = scheduleInput.getNote();
        }
        schedule.attend = 0;
        schedule.nonAttend = 0;
        schedule.isClose = false;
        schedule.viewCount = 0;
        return schedule;
    }

    /**
     * FIXME: 뭔가 지금 이 구조가 이상함. ScheduleVote 에서의 역할이 자꾸 여기서 처리되는듯. 이걸 ScheduleVoteService 에서 Boolean 값으로 넘겨주는게 맞는 듯
     * @param attendance
     */
    public void vote(String attendance) {
        if (getAttendanceType(attendance).equals(AttendanceType.ATTEND)) {
            this.attend += 1;
        } else {
            this.nonAttend += 1;
        }
    }

    public void reVote(boolean originalAttendance, boolean isAttendance) {
        // true - 참석 / false - 불참
        if (originalAttendance) {
            this.attend -= 1;
        } else  {
            this.nonAttend -= 1;
        }

        if (isAttendance) {
            this.attend += 1;
        } else {
            this.nonAttend += 1;
        }
    }

    public void update(ScheduleUpdateInput scheduleUpdateInput) {
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
            this.category = ScheduleCategory.fromKoreanName(scheduleUpdateInput.getCategory()).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.INVALID_SCHEDULE_CATEGORY));
        }
        if (scheduleUpdateInput.getNote() != null) {
            this.note = scheduleUpdateInput.getNote();
        }
    }

    public void close() {
        this.isClose = true;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }

    private AttendanceType getAttendanceType(String attendance) {
        return AttendanceType.fromKoreanName(attendance).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.INVALID_ATTENDANCE_TYPE));
    }

}
