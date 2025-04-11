package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.service.NoticeCommandService;
import com.example.moim.club.service.NoticeQueryService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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

    /**
     * FIXME: 공지를 시간 순으로 정렬하지 않아도 되나?
     * @return
     */
    @GetMapping("/notice/{clubId}")
    public BaseResponse<List<NoticeOutput>> findNotices(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(noticeQueryService.findNotice(userDetailsImpl.getUser(), clubId), ResponseCode.OK);
    }
}
