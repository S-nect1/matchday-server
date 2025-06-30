package com.example.moim.schedule.vote.entity;

import com.example.moim.global.entity.BaseEntity;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ScheduleVote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    private Boolean isAttendance;

    public static ScheduleVote createScheduleVote(User user, Schedule schedule, boolean isAttendance) {
        ScheduleVote scheduleVote = new ScheduleVote();
        scheduleVote.user = user;
        scheduleVote.schedule = schedule;
        scheduleVote.isAttendance = isAttendance;
        return scheduleVote;
    }

    public void changeAttendance(boolean isAttendance) {
        this.isAttendance = isAttendance;
    }
}
