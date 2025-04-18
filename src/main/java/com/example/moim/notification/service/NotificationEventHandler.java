package com.example.moim.notification.service;

import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.*;
import com.example.moim.notification.entity.Notifications;
import com.example.moim.notification.repository.NotificationRepository;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
    private final NotificationRepository notificationRepository;
    private final UserClubRepository userClubRepository;
    private final UserRepository userRepository;

    @Async
    @EventListener
    public void handleClubJoinEvent(ClubJoinEvent clubJoinEvent) {
        log.info("이벤트 들어옴");
        sendEachNotification(userClubRepository.findAllByClub(clubJoinEvent.getClub()).stream()
                .map(userClub -> Notifications.createClubJoinNotification(clubJoinEvent, userClub.getUser())).toList());
    }

    @Async
    @EventListener
    public void handleScheduleSaveEvent(ScheduleSaveEvent scheduleSaveEvent) {
        log.info("이벤트 들어옴");
        sendEachNotification(userClubRepository.findAllByClub(scheduleSaveEvent.getSchedule().getClub()).stream()
                .map(userClub -> Notifications.createScheduleSaveNotification(scheduleSaveEvent, userClub.getUser())).toList());
    }

//    @Async
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @TransactionalEventListener
//    public void handleScheduleVoteEvent(ScheduleVoteEvent scheduleVoteEvent) {
//        log.info("이벤트 들어옴");
//        sendNotification(Notifications.createScheduleVoteNotification(scheduleVoteEvent));
//    }

    @EventListener
    public void handleScheduleEncourageEvent(ScheduleEncourageEvent scheduleEncourageEvent) {
        log.info("이벤트 들어옴");
        sendEachNotification(scheduleEncourageEvent.getUserList().stream().map(user -> Notifications.createScheduleEncourageEvent(scheduleEncourageEvent.getSchedule(), user)).toList());
    }

    @EventListener
    public void handleMatchRequestEvent(MatchRequestEvent matchRequestEvent) {
        log.info("이벤트 들어옴");
        sendEachNotification(userClubRepository.findAllByClub(matchRequestEvent.getClub()).stream()
                .map(userClub -> Notifications.createMatchRequestEvent(matchRequestEvent, userClub.getUser())).toList());
    }

    @EventListener
    public void handMatchInviteEvent(MatchInviteEvent matchInviteEvent) {
        log.info("이벤트 들어옴");
        sendEachNotification(userClubRepository.findAdminByClub(matchInviteEvent.getClub()).stream()
                .map(userClub -> Notifications.createMatchInviteEvent(matchInviteEvent, userClub.getUser())).toList());
    }


    private void sendNotification(Notifications notification) {
//        Message message = Message.builder()
//                .setToken(notification.getTargetUser().getFcmToken())
//                .setNotification(Notification.builder()
//                        .setTitle(notification.getTitle())
//                        .setBody(notification.getContent())
//                        .build())
//                .setWebpushConfig(WebpushConfig.builder().setNotification(WebpushNotification.builder()
//                        .setBadge("https://media.discordapp.net/attachments/1081467110200451092/1199660879071952906/MG.png?ex=65c35a42&is=65b0e542&hm=4b109d5d3ab5850eeacbd46d8f8eed253ab7bdb7ac4d28111e57cad3aba58b98&=&format=webp&quality=lossless")
//                        .build()).build())
//                .setAndroidConfig(AndroidConfig.builder().setNotification(AndroidNotification.builder()
//                        .setPriority(AndroidNotification.Priority.HIGH)
//                        .build()).build())
//                .setApnsConfig()
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            log.info("Successfully sent message: {}", response);
        notificationRepository.save(notification);
//        } catch (FirebaseMessagingException e) {
//            log.error("cannot send to user push message. error info : {}", e.getMessage());
//        }
    }

    private void sendEachNotification(List<Notifications> notification) {
//        Message message = Message.builder()
//                .setToken(notification.getTargetUser().getFcmToken())
//                .setNotification(Notification.builder()
//                        .setTitle(notification.getTitle())
//                        .setBody(notification.getContent())
//                        .build())
//                .setWebpushConfig(WebpushConfig.builder().setNotification(WebpushNotification.builder()
//                        .setBadge("https://media.discordapp.net/attachments/1081467110200451092/1199660879071952906/MG.png?ex=65c35a42&is=65b0e542&hm=4b109d5d3ab5850eeacbd46d8f8eed253ab7bdb7ac4d28111e57cad3aba58b98&=&format=webp&quality=lossless")
//                        .build()).build())
//                .setAndroidConfig(AndroidConfig.builder().setNotification(AndroidNotification.builder()
//                        .setPriority(AndroidNotification.Priority.HIGH)
//                        .build()).build())
//                .setApnsConfig()
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().sendEach(message);
//            log.info("Successfully sent message: {}", response);
        notificationRepository.saveAll(notification);
//        } catch (FirebaseMessagingException e) {
//            log.error("cannot send to user push message. error info : {}", e.getMessage());
//        }
    }
}
