package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ScheduleDeleteEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleDeleteNotificationEventHandler implements NotificationEventHandler<ScheduleDeleteEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof ScheduleDeleteEvent;
    }

    @Override
    public List<NotificationEntity> handle(ScheduleDeleteEvent event) {
        return userClubRepository.findAllByClub(event.getSchedule().getClub())
                        .stream()
                        .map(userClub -> NotificationEntity.create(event.getUser()
                                , NotificationType.SCHEDULE_DELETE
                                , NotificationType.SCHEDULE_DELETE.formatMessage(event.getSchedule().getTitle())
                                , event.getSchedule().getTitle()
                                , event.getSchedule().getId()
                        ))
                        .toList();
    }
}
