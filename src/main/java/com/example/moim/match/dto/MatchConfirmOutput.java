package com.example.moim.match.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;

@Data
public class MatchConfirmOutput {
    private Long awayClubId;

    public MatchConfirmOutput(Club awayClub) {
        this.awayClubId = awayClub.getId();
    }
}
