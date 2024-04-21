package com.example.moim.notification.controller;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.service.NotificationService;
import com.example.moim.user.dto.userDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs{
    private final NotificationService notificationService;

    @GetMapping(value = "/notice")
    public NotificationExistOutput noticeCheck(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return notificationService.checkNotice(userDetailsImpl.getUser());
    }

    @GetMapping("/notices")
    public List<NotificationOutput> noticeFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return notificationService.findNotice(userDetailsImpl.getUser());
    }
}
