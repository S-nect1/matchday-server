package com.example.moim.club.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubInviteInput {
    private Long clubId;
    @Schema(pattern = "userName#userId")
    private String user;
}
