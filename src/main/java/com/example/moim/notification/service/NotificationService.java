package com.example.moim.notification.service;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.repository.NotificationRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationExistOutput checkNotice(User user) {
        return new NotificationExistOutput(notificationRepository.existsByTargetUserAndIsRead(user, false));
    }

    public List<NotificationOutput> findNotice(User user) {
        return notificationRepository.findByTargetUser(user).stream().map(NotificationOutput::new).toList();
    }
}
