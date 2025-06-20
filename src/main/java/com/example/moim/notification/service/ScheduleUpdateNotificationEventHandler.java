package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.dto.ScheduleUpdateEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleUpdateNotificationEventHandler implements NotificationEventHandler<ScheduleUpdateEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof ScheduleUpdateEvent;
    }

    @Override
    public List<NotificationEntity> handle(ScheduleUpdateEvent event) {
        return userClubRepository.findAllByClub(event.getSchedule().getClub())
                .stream()
                .map(userClub -> NotificationEntity.create(event.getUser()
                        , NotificationType.SCHEDULE_UPDATE
                        , NotificationType.SCHEDULE_UPDATE.formatMessage(event.getSchedule().getTitle())
                        , event.getSchedule().getTitle()
                        , event.getSchedule().getId()
                ))
                .toList();
    }
}
