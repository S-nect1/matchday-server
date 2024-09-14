package com.example.moim.match.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchMainOutput {
    private String clubTitle;
    //전적 들어가야함

    private List<NearClubsOutput> nearClubs;
    private List<RegisteredMatchDto> registeredMatches;

    public MatchMainOutput(String clubTitle, List<NearClubsOutput> nearClubs, List<RegisteredMatchDto> registeredMatchDtos) {
        this.clubTitle = clubTitle;
        this.nearClubs = nearClubs;
        this.registeredMatches = registeredMatchDtos;
    }
}
