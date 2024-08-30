package com.example.moim.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatchRecordInput {
    @NotNull(message = "득점수를 입력해주세요.")
    private int score;
}
