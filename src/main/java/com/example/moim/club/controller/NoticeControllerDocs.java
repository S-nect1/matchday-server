package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "모임 공지 api", description = "모임(club) 공지 api 분리")
public interface NoticeControllerDocs {
    @Operation(summary = "공지 생성")
    BaseResponse noticeSave(NoticeInput noticeInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "공지 조회")
    BaseResponse<List<NoticeOutput>> noticeSave(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
}
