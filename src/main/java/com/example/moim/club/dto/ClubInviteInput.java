package com.example.moim.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClubInviteInput {
    private Long clubId;
    @Schema(pattern = "userName#userId")
    private String user;
}
