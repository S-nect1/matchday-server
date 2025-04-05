package com.example.moim.notification.dto;

import com.example.moim.match.entity.Match;
import com.example.moim.user.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MatchCancelUserEvent {
    private final Match match;
    private final User targetUser;
}
