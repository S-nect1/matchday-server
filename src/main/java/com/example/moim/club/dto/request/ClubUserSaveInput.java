package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ClubUserSaveInput {
    private Long clubId;
    private String clubPassword;

    @Builder
    public ClubUserSaveInput(Long clubId, String clubPassword) {
        this.clubId = clubId;
        this.clubPassword = clubPassword;
    }
}