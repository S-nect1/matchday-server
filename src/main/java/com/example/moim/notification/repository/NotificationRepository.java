package com.example.moim.notification.repository;

import com.example.moim.notification.entity.Notification;
import com.example.moim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Transactional(readOnly = true)
    Boolean existsByTargetUserAndIsRead(User user, Boolean isRead);

    List<Notification> findByTargetUser(User targetUser);
}
