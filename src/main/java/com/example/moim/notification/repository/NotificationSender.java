package com.example.moim.notification.repository;

import com.example.moim.notification.entity.NotificationEntity;

public interface NotificationSender {
    void send(NotificationEntity notificationEntity);
}
