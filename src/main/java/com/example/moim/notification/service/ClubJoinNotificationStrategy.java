package com.example.moim.notification.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClubJoinNotificationStrategy implements NotificationStrategy<ClubJoinEvent> {

    private final UserClubRepository userClubRepository;

    @Override
    public boolean supports(Object event) {
        return event instanceof ClubJoinEvent;
    }

    @Override
    public List<NotificationEntity> generate(ClubJoinEvent event) {
        return userClubRepository.findAllByClub(event.getClub())
                .stream()
                .map(userClub -> NotificationEntity.create(userClub.getUser()
                        , NotificationType.CLUB_JOIN
                        , NotificationType.CLUB_JOIN.formatMessage(
                                event.getUser().getName(),
                                event.getClub().getTitle())
                        , event.getClub().getTitle()
                        , event.getClub().getId())
                )
                .toList();
    }
}
