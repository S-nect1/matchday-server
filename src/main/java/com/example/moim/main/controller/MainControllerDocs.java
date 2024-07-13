package com.example.moim.main.controller;

import com.example.moim.main.dto.MainOutput;
import com.example.moim.main.dto.NoClubMainOutput;
import com.example.moim.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "메인 페이지 api")
public interface MainControllerDocs {

    @Operation(summary = "모임 가입 전 메인 페이지")
    NoClubMainOutput noClubMainPage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "모임 가입 후 메인 페이지")
    MainOutput mainPage (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long clubId);
}