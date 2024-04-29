package com.example.moim.notification.controller;

import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.user.dto.userDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "알림 api", description = "푸시 알림은 fcm 사용")
public interface NotificationControllerDocs {

    @Operation(summary = "새로운 알림 있는지 체크")
    NotificationExistOutput noticeCheck(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "알림 조회")
    List<NotificationOutput> noticeFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);
}
