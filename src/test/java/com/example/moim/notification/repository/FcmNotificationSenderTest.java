package com.example.moim.notification.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationStatus;
import com.example.moim.notification.entity.NotificationType;
import com.example.moim.user.entity.User;
import com.google.firebase.ErrorCode;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FcmNotificationSenderTest {

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @InjectMocks
    private FcmNotificationSender fcmNotificationSender;

    @Test
    @DisplayName("FCM 토큰이 유효한 경우 FirebaseMessaging.send()가 호출되고 상태가 SENT로 설정된다")
    void shouldCallFirebaseMessaging_andMarkAsSent() throws FirebaseMessagingException {
        // given
        User user = User.builder().fcmToken("valid_token").build();
        NotificationEntity notification = NotificationEntity.builder()
                .title("제목")
                .content("내용")
                .type(NotificationType.CLUB_JOIN)
                .targetUser(user)
                .build();

        try (MockedStatic<FirebaseMessaging> mocked = Mockito.mockStatic(FirebaseMessaging.class)) {
            mocked.when(FirebaseMessaging::getInstance).thenReturn(firebaseMessaging);
            when(firebaseMessaging.send(any(Message.class))).thenReturn("success_response");

            // when
            fcmNotificationSender.send(notification);

            // then
            verify(firebaseMessaging).send(any(Message.class));
            assertThat(notification.getStatus()).isEqualTo(NotificationStatus.SENT);
        }
    }

    @Test
    @DisplayName("FCM 토큰이 유효하지 않은 경우 FirebaseMessaging.send()가 호출되고 상태가 FAILED로 설정된다")
    void send_shouldMarkAsFailed_whenTokenIsNull() {
        // given
        User user = User.builder().fcmToken(null).build();
        NotificationEntity notification = NotificationEntity.builder()
                .title("제목")
                .content("내용")
                .type(NotificationType.CLUB_JOIN)
                .targetUser(user)
                .build();
        // when
        fcmNotificationSender.send(notification);

        // then
        assertThat(notification.getStatus()).isEqualTo(NotificationStatus.FAILED);
    }

    @Test
    @DisplayName("FirebaseMessagingException이 발생하면 상태가 FAILED로 설정된다")
    void shouldHandleFirebaseMessagingException() throws FirebaseMessagingException {
        // given
        User user = User.builder().fcmToken("valid-token").build();
        NotificationEntity notification = NotificationEntity.builder()
                .title("제목")
                .content("내용")
                .type(NotificationType.CLUB_JOIN)
                .targetUser(user)
                .build();

        try (MockedStatic<FirebaseMessaging> mocked = Mockito.mockStatic(FirebaseMessaging.class)) {
            mocked.when(FirebaseMessaging::getInstance).thenReturn(firebaseMessaging);
            FirebaseMessagingException exception = mock(FirebaseMessagingException.class);
            when(firebaseMessaging.send(any(Message.class))).thenThrow(exception);

            // when
            fcmNotificationSender.send(notification);

            // then
            assertThat(notification.getStatus()).isEqualTo(NotificationStatus.FAILED);
        }
    }
}
