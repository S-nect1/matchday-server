package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
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
    @Operation(summary = "모임 생성", description = "응답으로 받는 profileImg, backgroundImg는 Base64 디코딩 필요")
    ClubOutput clubSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubInput clubInput) throws IOException;

    @Operation(summary = "모임 수정", description = "응답으로 받는 profileImg, backgroundImg는 Base64 디코딩 필요")
    ClubOutput clubUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput) throws IOException;

    @Operation(summary = "모임 가입")
    UserClubOutput clubUserSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubInput);

    @Operation(summary = "모임 회원 직책, 분류 수정")
    UserClubOutput clubUserUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput);

    @Operation(summary = "모임 정보 조회", description = "응답으로 받는 profileImg, backgroundImg는 Base64 디코딩 필요")
    ClubOutput clubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @PathVariable Long id);

    @Operation(summary = "모임 프사 변경")
    void profileImgUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubImgInput clubImgInput) throws IOException;

    @Operation(summary = "모임 배경사진 변경")
    void backgroundImgUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubImgInput clubImgInput) throws IOException;
}
