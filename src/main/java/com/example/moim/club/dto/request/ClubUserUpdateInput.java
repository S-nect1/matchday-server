package com.example.moim.club.dto.request;

import com.example.moim.global.enums.ClubRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubUserUpdateInput {
    private Long userId;
    private String clubRole;

    @Builder
    public ClubUserUpdateInput(Long userId, String clubRole) {
        this.userId = userId;
        this.clubRole = clubRole;
    }
}
