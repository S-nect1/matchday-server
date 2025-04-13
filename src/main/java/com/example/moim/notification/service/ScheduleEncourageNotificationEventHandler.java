package com.example.moim.notification.service;

import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEncourageNotificationEventHandler implements NotificationEventHandler<ScheduleEncourageEvent> {

    @Override
    public boolean canHandle(Object event) {
        return event instanceof ScheduleEncourageEvent;
    }

    @Override
    public List<NotificationEntity> handle(ScheduleEncourageEvent event) {
        return event.getUserList().stream()
                .map(user -> NotificationEntity.create(
                        user
                        , NotificationType.SCHEDULE_ENCOURAGE
                        , NotificationType.SCHEDULE_ENCOURAGE.formatMessage(
                                event.getSchedule().getTitle()
                        )
                        , event.getSchedule().getTitle()
                        , event.getSchedule().getId()
                )).toList();
    }
}
