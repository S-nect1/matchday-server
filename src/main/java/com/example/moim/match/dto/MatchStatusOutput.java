package com.example.moim.match.dto;


import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchStatusOutput {
    private Long id;
    private MatchStatus matchStatus;
    private LocalDate matchDate;
    private String period;
    private String name;
    private String location;

    public MatchStatusOutput(Match match) {
        this.id = getId();
        this.matchStatus = match.getMatchStatus();
        this.matchDate = match.getStartTime().toLocalDate();
        this.period = match.getStartTime().toLocalTime().toString() + " ~ " + match.getEndTime().toLocalTime().toString();
        this.name = match.getName();
        this.location = match.getLocation();
    }
}
