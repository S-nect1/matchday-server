package com.example.moim.notification.repository;

import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    @Transactional(readOnly = true)
    Boolean existsByTargetUserAndIsRead(User user, Boolean isRead);

    List<NotificationEntity> findByTargetUser(User targetUser);
}
