package com.example.moim.club.controller;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.dto.ClubOutput;
import com.example.moim.club.dto.ClubUserUpdateInput;
import com.example.moim.club.dto.UserClubOutput;
import com.example.moim.user.dto.userDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "모임 api")
public interface ClubControllerDocs {
    @Operation(summary = "모임 생성")
    ClubOutput clubSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubInput clubInput) throws IOException;

    @Operation(summary = "모임 회원 직책, 분류 수정")
    UserClubOutput clubUserUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput);

    @Operation(summary = "모임 정보 조회")
    ClubOutput clubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @PathVariable Long id);
}
