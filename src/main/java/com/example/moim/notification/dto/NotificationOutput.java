package com.example.moim.notification.dto;

import com.example.moim.notification.entity.NotificationEntity;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class NotificationOutput {
    private Long id;
    private String notificationType;
    private String title;
    private String content;
    private String createdAt;
    private Boolean isRead;
    private String actionUrl;

    public NotificationOutput(NotificationEntity notificationEntity) {
        this.id = notificationEntity.getId();
        this.title = notificationEntity.getTitle();
        this.notificationType = notificationEntity.getType().name();
        this.content = notificationEntity.getContent();
        this.createdAt = notificationEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.isRead = notificationEntity.getIsRead();
        this.actionUrl = notificationEntity.getType().getCategory() + "/" + notificationEntity.getLinkedId();
    }
}
