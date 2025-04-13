package com.example.moim.notification.service;

import com.example.moim.notification.dto.MatchCancelUserEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchCancelUserEventNotificationEventHandler implements NotificationEventHandler<MatchCancelUserEvent> {

    @Override
    public boolean canHandle(Object event) {
        return event instanceof MatchCancelUserEvent;
    }

    @Override
    public List<NotificationEntity> handle(MatchCancelUserEvent event) {
        return List.of(
                NotificationEntity.create(event.getTargetUser()
                        , NotificationType.MATCH_CANCEL_USER
                        , NotificationType.MATCH_CANCEL_USER.formatMessage(
                                event.getMatch().getName()
                        )
                        , event.getMatch().getName()
                        , event.getMatch().getId()
                )
        );
    }
}
