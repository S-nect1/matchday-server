package com.example.moim.notification.dto;

import com.example.moim.club.entity.Club;
import com.example.moim.match.entity.Match;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MatchCancelClubEvent {
    private final Match match;
    private final Club targetClub;
}
