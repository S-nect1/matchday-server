package com.example.moim.match.dto;

import com.example.moim.global.entity.EventType;
import lombok.Data;

@Data
public class MatchClubSearchCond {
    private String search;
    //    private String teamAbility;
    private String ageRange;
    private String gender;
    private String matchType;
}
