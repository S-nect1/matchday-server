package com.example.moim.match.dto;

import lombok.Data;

@Data
public class MatchApplyInput {
    private Long clubId;
    private Long id;
    private boolean isBall;
    private String note;
}
