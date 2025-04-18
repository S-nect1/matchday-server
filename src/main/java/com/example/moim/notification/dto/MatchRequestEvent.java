package com.example.moim.notification.dto;

import com.example.moim.club.entity.Club;
import com.example.moim.match.entity.Match;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class MatchRequestEvent {

    private Match match;
    private User user;
    private Club club;

    public MatchRequestEvent(Match match, User user, Club club) {
        this.match = match;
        this.user = user;
        this.club = club;
    }
}
