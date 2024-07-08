package com.example.moim.notification.entity;

import com.example.moim.club.entity.Schedule;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.dto.ScheduleVoteEvent;
import com.example.moim.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notifications extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User targetUser;
    private String title;
    private String category;
    private String contents;
    private Boolean isRead;

    public static Notifications createScheduleSaveNotification(ScheduleSaveEvent scheduleSaveEvent, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        Schedule schedule = scheduleSaveEvent.getSchedule();
        notifications.title = schedule.getClub().getTitle() + ": 새로운 일정 등록";
        notifications.category = "일정";
        notifications.contents = schedule.getTitle() + " 일정이 등록되었습니다.\n참가신청 바로가기!";
        notifications.isRead = false;
        return notifications;
    }

    public static Notifications createScheduleVoteNotification(ScheduleVoteEvent scheduleVoteEvent) {
        Notifications notifications = new Notifications();
        notifications.targetUser = scheduleVoteEvent.getUser();
        Schedule schedule = scheduleVoteEvent.getSchedule();
        notifications.title = schedule.getClub().getTitle() + ": 일정 참여";
        notifications.category = "일정";
        notifications.contents = schedule.getTitle() + " 일정에 참여했습니다.";
        notifications.isRead = false;
        return notifications;
    }

    public static Notifications ScheduleEncourageEvent(Schedule schedule, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        notifications.title = schedule.getClub().getTitle() + ": 일정 참가 독려";
        notifications.category = "일정";
        notifications.contents = schedule.getTitle() + " 일정에 참가해보세요!";
        notifications.isRead = false;
        return notifications;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }


}
