package com.example.moim.match.dto;

import com.example.moim.match.entity.MatchApplication;
import lombok.Data;

@Data
public class MatchApplyClubOutput {
    private String title;

    public MatchApplyClubOutput(MatchApplication matchApplication) {
        this.title = matchApplication.getClub().getTitle();
    }
}


