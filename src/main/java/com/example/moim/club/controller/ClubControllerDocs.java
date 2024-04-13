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
    @Operation(summary = "모임 생성", description = "profileImg, backgroundImg는 Base64 디코딩 필요")
    ClubOutput clubSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubInput clubInput) throws IOException;

    @Operation(summary = "모임 회원 직책, 분류 수정")
    UserClubOutput clubUserUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput);

    @Operation(summary = "모임 정보 조회", description = "profileImg, backgroundImg는 Base64 디코딩 필요")
    ClubOutput clubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @PathVariable Long id);

    @Operation(summary = "모임 프사 변경")
    void profileImgUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubImgInput clubImgInput) throws IOException;

    @Operation(summary = "모임 배경사진 변경")
    void backgroundImgUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubImgInput clubImgInput) throws IOException;
}
