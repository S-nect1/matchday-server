package com.example.moim.match.dto;

import com.example.moim.match.entity.MatchUser;
import lombok.Data;

@Data
public class MatchRecordOutput {
    private Long id;
    private Long userId;
    private int score;

    public MatchRecordOutput(MatchUser matchUser) {
        this.id = matchUser.getId();
        this.userId = matchUser.getUser().getId();
        this.score = matchUser.getScore();
    }
}
