package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.MatchCancelClubEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchCancelClubNotificationEventHandler implements NotificationEventHandler<MatchCancelClubEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof MatchCancelClubEvent;
    }

    @Override
    public List<NotificationEntity> handle(MatchCancelClubEvent event) {
        return userClubRepository.findAllByClub(event.getTargetClub()).stream()
                .map(userClub -> NotificationEntity.create(userClub.getUser()
                        , NotificationType.MATCH_CANCEL_CLUB
                        , NotificationType.MATCH_CANCEL_CLUB.formatMessage(
                                event.getTargetClub().getTitle()
                        )
                        , event.getMatch().getName()
                        , event.getMatch().getId()
                )).toList();
    }
}
