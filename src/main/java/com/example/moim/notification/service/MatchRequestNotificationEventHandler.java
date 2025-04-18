package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.MatchRequestEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchRequestNotificationEventHandler implements NotificationEventHandler<MatchRequestEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof MatchRequestEvent;
    }

    @Override
    public List<NotificationEntity> handle(MatchRequestEvent event) {
        return userClubRepository.findAllByClub(event.getClub()).stream()
                .map(userClub ->
                        NotificationEntity.create(event.getUser()
                                , NotificationType.MATCH_SUGGESTION
                                , NotificationType.MATCH_SUGGESTION.formatMessage(
                                        event.getClub().getTitle()
                                )
                                , event.getMatch().getName()
                                , event.getMatch().getId()))
                .toList();
    }
}
