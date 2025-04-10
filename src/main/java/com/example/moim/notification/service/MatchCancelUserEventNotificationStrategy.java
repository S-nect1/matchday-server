package com.example.moim.notification.service;

import com.example.moim.notification.dto.MatchCancelUserEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchCancelUserEventNotificationStrategy implements NotificationStrategy<MatchCancelUserEvent> {

    @Override
    public boolean supports(Object event) {
        return event instanceof MatchCancelUserEvent;
    }

    @Override
    public List<NotificationEntity> generate(MatchCancelUserEvent event) {
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
