package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.service.NoticeCommandService;
import com.example.moim.club.service.NoticeQueryService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {
    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    @PostMapping("/notice")
    public BaseResponse<NoticeOutput> saveNotice(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, NoticeInput noticeInput) {
        NoticeOutput noticeOutput = noticeCommandService.saveNotice(userDetailsImpl.getUser(), noticeInput);
        return BaseResponse.onSuccess(noticeOutput, ResponseCode.OK);
    }

    @GetMapping("/notice/{clubId}")
    public BaseResponse<Slice<NoticeOutput>> findNotices(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam("cursorId") Long cursorId) {
        return BaseResponse.onSuccess(noticeQueryService.findNotice(userDetailsImpl.getUser(), clubId, cursorId), ResponseCode.OK);
    }
}
