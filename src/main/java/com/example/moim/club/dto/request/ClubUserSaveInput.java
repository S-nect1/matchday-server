package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubUserSaveInput {
    private String clubPassword;

    @Builder
    public ClubUserSaveInput(Long clubId, String clubPassword) {
        this.clubPassword = clubPassword;
    }
}