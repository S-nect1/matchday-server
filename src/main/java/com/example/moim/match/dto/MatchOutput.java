package com.example.moim.match.dto;

import lombok.Data;

@Data
public class MatchOutput {
    private Long id;

    public MatchOutput(Long id) {
        this.id = id;
    }
}


