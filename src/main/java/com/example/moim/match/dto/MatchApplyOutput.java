package com.example.moim.match.dto;

import lombok.Data;

@Data
public class MatchApplyOutput {
    private Long id;

    public MatchApplyOutput(Long id) {
        this.id = id;
    }
}
