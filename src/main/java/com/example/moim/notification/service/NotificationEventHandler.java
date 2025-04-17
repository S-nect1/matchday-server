package com.example.moim.notification.service;

import com.example.moim.notification.entity.NotificationEntity;
import java.util.List;

public interface NotificationEventHandler<T> {
    boolean canHandle(Object event);  // 타입 판별용
    List<NotificationEntity> handle(T event);
}
