package com.example.moim.notification.repository;

import com.example.moim.notification.entity.NotificationEntity;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmNotificationSender implements NotificationSender {
    @Override
    public void send(NotificationEntity notificationEntity) {
        String fcmToken = notificationEntity.getTargetUser().getFcmToken();
        if (fcmToken == null || fcmToken.isBlank()) {
            log.warn("🔴 FCM 토큰이 존재하지 않아 푸시 전송 생략: userId={}", notificationEntity.getTargetUser().getId());
            notificationEntity.failed();
            return;
        }

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                        .setTitle(notificationEntity.getTitle())
                        .setBody(notificationEntity.getContent())
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setPriority(AndroidNotification.Priority.HIGH)
                                .build())
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(
                                WebpushNotification.builder()
                                        .setBadge(
                                                "https://media.discordapp.net/attachments/1081467110200451092/1199660879071952906/MG.png?ex=65c35a42&is=65b0e542&hm=4b109d5d3ab5850eeacbd46d8f8eed253ab7bdb7ac4d28111e57cad3aba58b98&=&format=webp&quality=lossless")
                                        .build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setBadge(1)
                                .build())
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ FCM 푸시 전송 성공: userId={}, response={}", notificationEntity.getTargetUser().getId(), response);
            notificationEntity.sent();
        } catch (FirebaseMessagingException e) {
            log.error("🔴 FCM 푸시 전송 실패: userId={}, 이유={}", notificationEntity.getTargetUser().getId(), e.getMessage(),
                    e);
            notificationEntity.failed();
            // TODO: 실패한 알림을 재전송하는 로직 추가 필요
        }
    }
}
