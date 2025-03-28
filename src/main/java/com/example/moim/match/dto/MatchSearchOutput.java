package com.example.moim.match.dto;

import com.example.moim.global.entity.EventType;
import com.example.moim.match.entity.Match;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchSearchOutput {
    private Long id;
    private LocalDate matchDate;
    private String period;
    private String name;
    private EventType event;
    private String location;

    public MatchSearchOutput(Match match) {
        this.id = match.getId();
        this.matchDate = match.getStartTime().toLocalDate();
        this.period = match.getStartTime().toLocalTime().toString() + " ~ " + match.getEndTime().toLocalTime().toString();
        this.name = match.getName();
        this.event = match.getEvent();
        this.location = match.getLocation();
    }
}
