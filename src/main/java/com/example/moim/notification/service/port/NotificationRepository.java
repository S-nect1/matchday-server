package com.example.moim.notification.service.port;

import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.user.entity.User;
import java.util.List;

public interface NotificationRepository {
    Boolean existsByTargetUserAndIsRead(User user, Boolean isRead);

    List<NotificationEntity> findByTargetUser(User user);

    void deleteById(Long id);

    void saveAll(List<NotificationEntity> notificationEntities);
}
