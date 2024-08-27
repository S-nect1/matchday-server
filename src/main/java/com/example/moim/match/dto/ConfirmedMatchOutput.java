package com.example.moim.match.dto;

import com.example.moim.match.entity.Match;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConfirmedMatchOutput {
    private String event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate matchDate;
    private String location;
    private String period;

    public ConfirmedMatchOutput(Match match) {
        this.event = match.getEvent();
        this.matchDate = match.getStartTime().toLocalDate();
        this.location = match.getLocation();
        this.period = match.getStartTime().toLocalTime().toString() + " ~ " + match.getEndTime().toLocalTime().toString();
    }
}
