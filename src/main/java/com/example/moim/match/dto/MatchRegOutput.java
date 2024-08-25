package com.example.moim.match.dto;

import lombok.Data;

@Data
public class MatchRegOutput {
    private Long id;

    public MatchRegOutput(Long id) {
        this.id = id;
    }
}
