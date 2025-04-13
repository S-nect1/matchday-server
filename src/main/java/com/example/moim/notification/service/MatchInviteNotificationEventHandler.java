package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.MatchInviteEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchInviteNotificationEventHandler implements NotificationEventHandler<MatchInviteEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof MatchInviteEvent;
    }

    @Override
    public List<NotificationEntity> handle(MatchInviteEvent event) {
        return userClubRepository.findAllByClub(event.getClub())
                .stream()
                .map(userClub -> NotificationEntity.create(userClub.getUser()
                        , NotificationType.MATCH_INVITE
                        , NotificationType.MATCH_INVITE.formatMessage(event.getClub().getTitle())
                        , event.getClub().getTitle()
                        , event.getClub().getId())
                )
                .toList();
    }
}
