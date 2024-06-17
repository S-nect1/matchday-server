package com.example.moim.main.controller;

import com.example.moim.main.dto.NoClubMainOutput;
import com.example.moim.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "모임 가입 전 메인 페이지 api")
public interface MainControllerDocs {

    @Operation(summary = "모임 가입 전 메인 페이지")
    NoClubMainOutput noClubMainPage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
}