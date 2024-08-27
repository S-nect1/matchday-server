package com.example.moim.notification.dto;

import com.example.moim.match.entity.MatchApplication;
import com.example.moim.user.entity.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class MatchAppVoteEvent {

    private MatchApplication matchApplication;
    private User user;

    public MatchAppVoteEvent(MatchApplication matchApplication, User user) {
        this.matchApplication = matchApplication;
        this.user = user;
    }
}
