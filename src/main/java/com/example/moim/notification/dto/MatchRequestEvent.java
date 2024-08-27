package com.example.moim.notification.dto;

import com.example.moim.match.entity.Match;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class MatchRequestEvent {

    private Match match;
    private User user;

    public MatchRequestEvent(Match match, User user) {
        this.match = match;
        this.user = user;
    }
}
