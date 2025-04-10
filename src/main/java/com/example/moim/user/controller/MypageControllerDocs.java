package com.example.moim.user.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.user.dto.MypageClubOutput;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.dto.UserUpdateInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@Tag(name = "마이페이지 api")
public interface MypageControllerDocs {

    @Operation(summary = "유저 정보 수정")
    BaseResponse<Void> userInfoUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid UserUpdateInput userUpdateInput) throws IOException;

    @Operation(summary = "마이페이지 내가 속한 모임 조회")
    BaseResponse<List<MypageClubOutput>> findMypageClub(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "유저 소속 모임 탈퇴")
    BaseResponse<Void> userClubDelete(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
}
