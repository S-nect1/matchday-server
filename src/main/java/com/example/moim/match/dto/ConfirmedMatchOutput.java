package com.example.moim.match.dto;

import com.example.moim.club.entity.Club;
import com.example.moim.global.entity.EventType;
import com.example.moim.match.entity.Match;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConfirmedMatchOutput {
    private String OpponentClubName;
    private EventType event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate matchDate;
    private String location;
    private String period;

    public ConfirmedMatchOutput(Match match, Club club) {
        this.OpponentClubName = match.findOpponentClub(club).getTitle();
        this.event = match.getEvent();
        this.matchDate = match.getStartTime().toLocalDate();
        this.location = match.getLocation();
        this.period = match.getStartTime().toLocalTime().toString() + " ~ " + match.getEndTime().toLocalTime().toString();
    }

}
