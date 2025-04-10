package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleSaveNotificationStrategy implements NotificationStrategy<ScheduleSaveEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean supports(Object event) {
        return event instanceof ScheduleSaveEvent;
    }

    @Override
    public List<NotificationEntity> generate(ScheduleSaveEvent event) {
        return userClubRepository.findAllByClub(event.getSchedule().getClub())
                        .stream()
                        .map(userClub -> NotificationEntity.create(event.getUser()
                                , NotificationType.SCHEDULE_SAVE
                                , NotificationType.SCHEDULE_SAVE.formatMessage(event.getSchedule().getTitle())
                                , event.getSchedule().getTitle()
                                , event.getSchedule().getId()
                        ))
                        .toList();
    }
}
