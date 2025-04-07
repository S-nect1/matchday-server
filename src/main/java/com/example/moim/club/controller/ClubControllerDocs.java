package com.example.moim.club.controller;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.*;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Tag(name = "모임 api", description = "모임(club) 안에서 유저의 category에 따라 권한 부여. creator, admin / member, newmember")
public interface ClubControllerDocs {
    @Operation(summary = "모임 생성", description = "응답으로 받는 profileImg는 Base64 디코딩 필요")
    BaseResponse<ClubSaveOutput> clubSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubInput clubInput) throws IOException;
//    BaseResponse<ClubSaveOutput> clubSave(@ModelAttribute ClubInput clubInput) throws IOException;

    @Operation(summary = "모임 수정", description = "응답으로 받는 profileImg는 Base64 디코딩 필요")
    BaseResponse<ClubUpdateOutput> clubUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput, @PathVariable("id") Long clubId) throws IOException;
//    BaseResponse<ClubUpdateOutput> clubUpdate(@ModelAttribute ClubUpdateInput clubUpdateInput, @PathVariable Long clubId) throws IOException;

    @Operation(summary = "모임 검색")
    BaseResponse<List<ClubSearchOutput>> findClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubSearchCond clubSearchCond);
//    BaseResponse<List<ClubSearchOutput>> findClubs(@ModelAttribute ClubSearchCond clubSearchCond);

    @Operation(summary = "모임 가입")
    BaseResponse<UserClubOutput> clubUserSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubInput, @PathVariable("id") Long clubId);
//    BaseResponse<UserClubOutput> clubUserSave(@RequestBody ClubUserSaveInput clubInput, @PathVariable Long clubId);

//    @Operation(summary = "모임에 초대")
//    UserClubOutput clubUserInvite(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubInput);

    @Operation(summary = "모임 회원 직책, 분류 수정")
    BaseResponse<UserClubOutput> clubUserUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput, @PathVariable("id") Long clubId);
//    BaseResponse<UserClubOutput> clubUserUpdate(@RequestBody ClubUserUpdateInput clubInput, @PathVariable("id") Long clubId);

    @Operation(summary = "모임 정보 조회", description = "응답으로 받는 profileImg는 Base64 디코딩 필요")
    BaseResponse<ClubOutput> clubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable("id") Long clubId);
//    BaseResponse<ClubOutput> clubFind(@PathVariable("id") Long clubId);

    @Operation(summary = "모임 비밀번호 변경")
    BaseResponse clubPasswordUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubPswdUpdateInput clubPswdUpdateInput, @PathVariable("id") Long clubId);
//    BaseResponse clubPasswordUpdate(@RequestBody ClubPswdUpdateInput clubPswdUpdateInput, @PathVariable Long clubId);

//    @Operation(summary = "모임 사진 변경")
//    void profileImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubImgInput clubImgInput) throws IOException;

}
