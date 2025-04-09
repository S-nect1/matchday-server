package com.example.moim.notification.service;

import com.example.moim.notification.entity.NotificationEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventDispatcher {

    private final List<NotificationStrategy<?>> strategies;
    private final NotificationService notificationService;

    @Async
    @EventListener
    public void dispatchEvent(Object event) {
        strategies.stream()
                .filter(strategy -> strategy.supports(event))
                .findFirst()
                .ifPresent(strategy -> {
                    @SuppressWarnings("unchecked")
                    NotificationStrategy<Object> s = (NotificationStrategy<Object>) strategy;
                    List<NotificationEntity> notificationEntities = s.generate(event);
//                    notificationService.sendAll(notificationEntities);
                });
    }
}