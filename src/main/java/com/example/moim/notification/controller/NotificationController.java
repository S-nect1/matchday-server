package com.example.moim.notification.controller;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.service.NotificationService;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs{
    private final NotificationService notificationService;

    @GetMapping(value = "/notifications/unread-count")
    public NotificationExistOutput notificationUnreadCount(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return notificationService.checkNotificationUnread(userDetailsImpl.getUser());
    }

    @GetMapping("/notifications")
    public List<NotificationOutput> notificationFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return notificationService.findNotifications(userDetailsImpl.getUser());
    }

    @DeleteMapping("/notifications/{id}")
    public void notificationRemove(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
        notificationService.removeNotification(id);
    }
}
