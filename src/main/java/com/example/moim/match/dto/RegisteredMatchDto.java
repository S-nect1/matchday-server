package com.example.moim.match.dto;

import com.example.moim.match.entity.Match;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisteredMatchDto implements Comparable<RegisteredMatchDto> {
    private String opponentClubName;
    private String event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate matchDate;
    private String location;
    private String period;
    private Double distance;

    public RegisteredMatchDto(Match match, Double distance) {
        this.opponentClubName = match.getHomeClub().getTitle();
        this.event = match.getEvent();
        this.matchDate = match.getStartTime().toLocalDate();
        this.location = match.getLocation();
        this.period = match.getStartTime().toLocalTime().toString() + " ~ " + match.getEndTime().toLocalTime().toString();
    }

    @Override
    public int compareTo(RegisteredMatchDto o) {
        if (this.getDistance() < o.getDistance()) {
            return -1;
        } else if (this.getDistance() > o.getDistance()) {
            return 1;
        }
        return 0;
    }
}
