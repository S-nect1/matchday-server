package com.example.moim.match.dto;

import com.example.moim.match.entity.Status;
import lombok.Data;

@Data
public class MatchRegInput {
    private boolean isBall;
    private String note;
}
