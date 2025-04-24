package com.example.moim.notification.dto;

import com.example.moim.club.entity.Club;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class NoticeSaveEvent {
    User user;
    Club club;
    public NoticeSaveEvent(User user, Club club) {
        this.user = user;
        this.club = club;
    }
}
