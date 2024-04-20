package com.example.moim.notification.service;

import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.entity.Notifications;
import com.example.moim.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
    private final NotificationRepository notificationRepository;

    @Async
    @EventListener
    public void handleEvaluateBooksEvent(ScheduleSaveEvent scheduleSaveEvent) {
        log.info("이벤트 들어옴");
        sendNotification(Notifications.createScheduleSaveNotification(scheduleSaveEvent));
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
}
