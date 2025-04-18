package com.example.moim.notification.controller;

import com.example.moim.notification.controller.port.NotificationService;
import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.user.dto.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs {
    private final NotificationService notificationService;

    @GetMapping(value = "/notification/unread-count")
    public NotificationExistOutput notificationUnreadCount(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return notificationService.checkUnread(userDetailsImpl.getUser());
    }

    @GetMapping("/notification")
    public List<NotificationOutput> notificationFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return notificationService.findAll(userDetailsImpl.getUser());
    }

    @DeleteMapping("/notification/{id}")
    public void notificationRemove(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
        notificationService.remove(id);
    }
}
