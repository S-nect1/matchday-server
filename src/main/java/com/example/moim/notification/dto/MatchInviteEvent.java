package com.example.moim.notification.dto;

import com.example.moim.club.entity.Club;
import com.example.moim.match.entity.Match;
import com.example.moim.user.entity.User;
import lombok.Data;

@Data
public class MatchInviteEvent {

    private Match match;
    private Club club;
    private User user;

    public MatchInviteEvent(Match match, Club club, User user) {
        this.match = match;
        this.club = club;
        this.user = user;
    }
}
