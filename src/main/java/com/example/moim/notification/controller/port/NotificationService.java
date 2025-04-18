package com.example.moim.notification.controller.port;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.user.entity.User;
import java.util.List;

public interface NotificationService {
    NotificationExistOutput checkUnread(User user);

    List<NotificationOutput> findAll(User user);

    void remove(Long id);

    void sendAll(List<NotificationEntity> notificationEntities);
}
