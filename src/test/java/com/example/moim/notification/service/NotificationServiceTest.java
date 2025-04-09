package com.example.moim.notification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moim.club.entity.Club;
import com.example.moim.notification.controller.port.NotificationService;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.notification.dto.NotificationExistOutput;
import com.example.moim.notification.dto.NotificationOutput;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import com.example.moim.notification.repository.NotificationJpaRepository;
import com.example.moim.notification.service.port.NotificationRepository;
import com.example.moim.user.entity.User;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("사용자에게 읽지 않은 알림이 없으면 false 반환한다")
    void shouldReturnFalseWhenNoUnreadNotifications() {
        // given
        User user = new User();
        when(notificationRepository.existsByTargetUserAndIsRead(user, false)).thenReturn(false);

        // when
        NotificationExistOutput result = notificationService.checkUnread(user);

        // then
        assertFalse(result.getHasNotice());
    }

    @Test
    @DisplayName("사용자에게 읽지 않은 알림이 있으면 true 반환한다")
    void shouldReturnTrueWhenUnreadNotificationsExist() {
        // given
        User user = new User();
        when(notificationRepository.existsByTargetUserAndIsRead(user, false)).thenReturn(true);

        // when
        NotificationExistOutput result = notificationService.checkUnread(user);

        // then
        assertTrue(result.getHasNotice());
    }

    @Test
    @DisplayName("사용자에게 온 알림이 없으면 빈 목록을 반환한다")
    void shouldReturnEmptyListWhenNoNotificationsReceived() {
        // given
        User user = new User();
        when(notificationRepository.findByTargetUser(user)).thenReturn(Collections.emptyList());

        // when
        List<NotificationOutput> result = notificationService.findAll(user);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("사용자에게 온 알림이 있으면 사용자의 알림 목록을 반환한다")
    void shouldReturnNotificationListWhenNotificationsExist() {
        // given
        User user = new User();
        Club club = new Club();
        ClubJoinEvent clubJoinEvent = new ClubJoinEvent(user, club);
        NotificationEntity notificationEntity = NotificationEntity.create(clubJoinEvent.getUser()
                , NotificationType.CLUB_JOIN
                , NotificationType.CLUB_JOIN.formatMessage(
                        clubJoinEvent.getUser().getName()
                        , clubJoinEvent.getClub().getTitle())
                , clubJoinEvent.getClub().getTitle()
                , clubJoinEvent.getClub().getId());
        notificationEntity.setCreatedDate();
        List<NotificationEntity> notificationEntities = List.of(notificationEntity);
        when(notificationRepository.findByTargetUser(user)).thenReturn(notificationEntities);

        // when
        List<NotificationOutput> result = notificationService.findAll(user);

        // then
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("존재하지 않는 ID를 삭제하려고 할 때 예외가 발생하지 않아도 된다")
    void shouldNotThrowExceptionWhenRemovingNonExistentNotification() {
        // given
        Long nonExistentId = 999L;
        doNothing().when(notificationRepository).deleteById(nonExistentId);

        // when
        notificationService.remove(nonExistentId);

        // then
        verify(notificationRepository, times(1)).deleteById(nonExistentId);
    }

    @Test
    @DisplayName("알림 ID로 알림을 삭제할 수 있다") // FIXME : 근데 알림 삭제가 왜 필요하지? 읽음 처리도 아니고?
    void shouldDeleteNotificationById() {
        // given
        Long notificationId = 1L;
        doNothing().when(notificationRepository).deleteById(notificationId);

        // when
        notificationService.remove(notificationId);

        // then
        verify(notificationRepository, times(1)).deleteById(notificationId);
    }
}