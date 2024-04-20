package com.example.moim.notification.dto;

import lombok.Data;

@Data
public class NotificationExistOutput {
    private Boolean hasNotice;

    public NotificationExistOutput(Boolean hasNotice) {
        this.hasNotice = hasNotice;
    }
}
