package com.example.moim.notification.service;

import com.example.moim.notification.controller.port.NotificationService;
import com.example.moim.notification.entity.NotificationEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventDispatcher {

    private final List<NotificationEventHandler<?>> handlers;
    private final NotificationService notificationService;

    @Async
    @EventListener
    public void dispatchEvent(Object event) {
        handlers.stream()
                .filter(strategy -> strategy.canHandle(event))
                .findFirst()
                .ifPresent(strategy -> {
                    @SuppressWarnings("unchecked")
                    NotificationEventHandler<Object> s = (NotificationEventHandler<Object>) strategy;
                    List<NotificationEntity> notificationEntities = s.handle(event);
                    notificationService.sendAll(notificationEntities);
                });
    }
}