package com.example.moim.notification.dto;

import com.example.moim.notification.entity.Notification;
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

    public NotificationOutput(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.category = notification.getCategory();
        this.content = notification.getContents();
        this.time = notification.getCreatedDate().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
        notification.setRead(true);
        this.isRead = notification.getIsRead();
    }
}
