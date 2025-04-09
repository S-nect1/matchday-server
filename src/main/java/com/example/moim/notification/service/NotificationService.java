package com.example.moim.notification.service;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.repository.NotificationRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;

    public NotificationExistOutput checkNotice(User user) {
    public NotificationExistOutput checkUnread(User user) {
        return new NotificationExistOutput(notificationRepository.existsByTargetUserAndIsRead(user, false));
    }

    public List<NotificationOutput> findNotice(User user) {
    public List<NotificationOutput> findAll(User user) {
        return notificationRepository.findByTargetUser(user).stream().map(NotificationOutput::new).toList();
    }

    @Transactional
    public void removeNotice(Long id) {
    public void remove(Long id) {
        notificationRepository.deleteById(id);
    }
}
