package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.notification.dto.NoticeSaveEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoticeSaveNotificationEventHandler implements NotificationEventHandler<NoticeSaveEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof NoticeSaveEvent;
    }

    @Override
    public List<NotificationEntity> handle(NoticeSaveEvent event) {
        return userClubRepository.findAllByClub(event.getClub())
                .stream()
                .map(userClub -> NotificationEntity.create(userClub.getUser()
                        , NotificationType.NOTICE_SAVE
                        , NotificationType.NOTICE_SAVE.formatMessage(
                                event.getUser().getName(),
                                event.getClub().getTitle())
                        , event.getClub().getTitle()
                        , event.getClub().getId())
                )
                .toList();
    }
}
