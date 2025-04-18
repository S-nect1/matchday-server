package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "모임 공지 api", description = "모임(club) 공지 api 분리")
public interface NoticeControllerDocs {
    @Operation(summary = "공지 생성")
    BaseResponse<NoticeOutput> saveNotice(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute NoticeInput noticeInput, @PathVariable("clubId") Long clubId);
//    BaseResponse<NoticeOutput> saveNotice(@ModelAttribute NoticeInput noticeInput, @PathVariable Long clubId);

    @Operation(summary = "공지 조회, 최초 요청 시 cursorId null 로 보내기")
    BaseResponse<Slice<NoticeOutput>> findNotices(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam Long cursorId);
//    BaseResponse<Slice<NoticeOutput>> findNotices(@PathVariable Long clubId, @RequestParam Long cursorId);
}
