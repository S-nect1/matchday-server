package com.example.moim.notification.repository;

import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.service.port.NotificationRepository;
import com.example.moim.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements
        NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Boolean existsByTargetUserAndIsRead(User user, Boolean isRead) {
        return notificationJpaRepository.existsByTargetUserAndIsRead(user, isRead);
    }

    @Override
    public List<NotificationEntity> findByTargetUser(User user) {
        return notificationJpaRepository.findByTargetUser(user);
    }

    @Override
    public void deleteById(Long id) {
        notificationJpaRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<NotificationEntity> notificationEntities) {
        notificationJpaRepository.saveAll(notificationEntities);
    }
}
