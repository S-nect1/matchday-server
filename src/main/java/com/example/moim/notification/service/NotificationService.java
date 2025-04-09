package com.example.moim.notification.service;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.repository.NotificationRepository;
import com.example.moim.notification.repository.NotificationSender;
import com.example.moim.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;

    public NotificationExistOutput checkUnread(User user) {
        return new NotificationExistOutput(notificationRepository.existsByTargetUserAndIsRead(user, false));
    }

    public List<NotificationOutput> findAll(User user) {
        return notificationRepository.findByTargetUser(user).stream().map(NotificationOutput::new).toList();
    }

    @Transactional
    public void remove(Long id) {
        notificationRepository.deleteById(id);
    }

    @Transactional
    public void sendAll(List<NotificationEntity> notificationEntities) {
        notificationRepository.saveAll(notificationEntities);
        notificationEntities.forEach(notificationSender::send);
    }
}
