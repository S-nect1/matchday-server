package com.example.moim.notification.dto;

import com.example.moim.notification.entity.Notifications;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class NotificationOutput {
    private Long id;
    private String title;
    private String category;
    private String content;
    private String time;
    private Boolean isRead;

    public NotificationOutput(Notifications notifications) {
        this.id = notifications.getId();
        this.title = notifications.getTitle();
        this.category = notifications.getCategory();
        this.content = notifications.getContents();
        this.time = notifications.getCreatedDate().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
        notifications.setRead(true);
        this.isRead = notifications.getIsRead();
    }
}
