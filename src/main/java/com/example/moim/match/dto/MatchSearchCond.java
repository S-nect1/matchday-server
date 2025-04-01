package com.example.moim.match.dto;

import com.example.moim.global.entity.EventType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchSearchCond {
    private String search;
    private LocalDate matchDate;
//    private String teamAbility;
    private String ageRange;
    private String area;
    private String gender;
    private String matchType;
}


