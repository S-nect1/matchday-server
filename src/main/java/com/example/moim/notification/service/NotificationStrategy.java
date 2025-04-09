package com.example.moim.notification.service;

import com.example.moim.notification.entity.NotificationEntity;
import java.util.List;

public interface NotificationStrategy<T> {
    boolean supports(Object event);  // 타입 판별용
    List<NotificationEntity> generate(T event);
}
