package com.example.moim.notification.entity;

import com.example.moim.global.entity.BaseEntity;
import com.example.moim.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User targetUser;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String title;
    private String content;
    private Long linkedId;  // 알림과 연관된 클럽, 일정, 매치 등의 ID
    private Boolean isRead;
    private NotificationStatus status;

    @Builder
    private NotificationEntity(User targetUser, NotificationType type, String title, String content, Long linkedId) {
        this.targetUser = targetUser;
        this.type = type;
        this.title = title;
        this.content = content;
        this.linkedId = linkedId;
        this.isRead = false;
        this.status = NotificationStatus.READY;
    }

    public static NotificationEntity create(User targetUser, NotificationType type, String content, String title, Long linkedId) {
        return NotificationEntity.builder()
                .targetUser(targetUser)
                .type(type)
                .content(content)
                .title(title)
                .linkedId(linkedId)
                .build();
    }

    public void read() {
        isRead = true;
    }

    public void sent() {
        status = NotificationStatus.SENT;
    }

    public void failed() {
        status = NotificationStatus.FAILED;
    }
}
