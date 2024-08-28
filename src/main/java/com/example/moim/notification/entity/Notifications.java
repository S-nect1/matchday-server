package com.example.moim.notification.entity;

import com.example.moim.club.entity.Schedule;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.match.entity.Match;
import com.example.moim.notification.dto.*;
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

    public static Notifications createClubJoinNotification(ClubJoinEvent clubJoinEvent, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        notifications.title = "가입 알림";
        notifications.category = "모임";
        notifications.contents = clubJoinEvent.getUser().getName() + " 님이 " + clubJoinEvent.getClub().getTitle() + "에 가입했습니다.";
        notifications.isRead = false;
        return notifications;
    }

    public static Notifications createScheduleSaveNotification(ScheduleSaveEvent scheduleSaveEvent, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        Schedule schedule = scheduleSaveEvent.getSchedule();
        notifications.title = "일정 등록 알림";
        notifications.category = "일정";
        notifications.contents = schedule.getTitle() + " 일정이 등록되었습니다.\n참가신청 바로가기!";
        notifications.isRead = false;
        return notifications;
    }

//    public static Notifications createScheduleVoteNotification(ScheduleVoteEvent scheduleVoteEvent) {
//        Notifications notifications = new Notifications();
//        notifications.targetUser = scheduleVoteEvent.getUser();
//        Schedule schedule = scheduleVoteEvent.getSchedule();
//        notifications.title = schedule.getClub().getTitle() + ": 일정 참여";
//        notifications.category = "일정";
//        notifications.contents = schedule.getTitle() + " 일정에 참여했습니다.";
//        notifications.isRead = false;
//        return notifications;
//    }

    public static Notifications createScheduleEncourageEvent(Schedule schedule, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        notifications.title = " 일정 참가 투표 알림";
        notifications.category = "일정";
        notifications.contents = schedule.getTitle() + " 일정 참가 투표가 곧 마감됩니다.\n참가 투표를 해주세요.";
        notifications.isRead = false;
        return notifications;
    }

    public static Notifications createMatchRequestEvent(MatchRequestEvent matchRequestEvent, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        notifications.title = "매치 건의 알림";
        notifications.category = "친선 매치";
        notifications.contents = matchRequestEvent.getUser().getName() + "님이 "
                + matchRequestEvent.getMatch().getStartTime().toLocalDate()
                + matchRequestEvent.getMatch().getEvent() + "매치를 원합니다. \nt승인하시겠습니까?";
        notifications.isRead = false;
        return notifications;
    }

    public static Notifications createMatchInviteEvent(MatchInviteEvent matchInviteEvent, User targetUser) {
        Notifications notifications = new Notifications();
        notifications.targetUser = targetUser;
        notifications.title = "매치 초청 알림";
        notifications.category = "친선 매치";
        notifications.contents = "<" + matchInviteEvent.getMatch().getEvent() + " 한판 해요~> - " +
                matchInviteEvent.getMatch().getHomeClub().getTitle() + "\n" +
                matchInviteEvent.getMatch().getHomeClub().getTitle() + "가 친선 매치를 제안했습니다!";
        notifications.isRead = false;
        return notifications;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }


}
