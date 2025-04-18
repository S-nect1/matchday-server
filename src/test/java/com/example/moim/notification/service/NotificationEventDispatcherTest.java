package com.example.moim.notification.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

import com.example.moim.notification.controller.port.NotificationService;
import com.example.moim.notification.entity.NotificationEntity;
import com.example.moim.notification.entity.NotificationType;
import com.example.moim.user.entity.User;
import java.time.Duration;
import java.util.List;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class NotificationEventDispatcherTest {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private NotificationService notificationService;

    @TestConfiguration
    static class TestStrategyConfig {
        @Bean
        public NotificationEventHandler<DummyEvent> dummyStrategy() {
            return new NotificationEventHandler<>() {
                @Override
                public boolean canHandle(Object event) {
                    return event instanceof DummyEvent;
                }

                @Override
                public List<NotificationEntity> handle(DummyEvent event) {
                    return List.of(NotificationEntity.builder()
                            .title("test")
                            .content("test")
                            .type(event.type())
                            .targetUser(event.user())
                            .build());
                }
            };
        }
    }

    @Test
    @DisplayName("이벤트가 발생하면 비동기로 알림을 전송한다")
    void dispatchEvent_shouldSendNotification_whenEventIsDispatched() {
        // given
        User user = User.builder().fcmToken("test-token").build();
        DummyEvent event = new DummyEvent(NotificationType.MATCH_SUCCESS, user);

        // when
        eventPublisher.publishEvent(event);

        // then
        // 비동기 처리를 기다리기 위해 Awaitility 또는 CountDownLatch 사용
        Awaitility.await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() ->
                        verify(notificationService).sendAll(anyList())
                );
    }

    private record DummyEvent(NotificationType type, User user) {
    }

}