package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.service.NoticeCommandService;
import com.example.moim.club.service.NoticeCommandServiceImpl;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {
    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    /**
     * FIXME: 응답 값이 무조건 있어야 함. user 의 권한 체크는 안해도 되나?
     */
    @PostMapping("/notice")
    public BaseResponse noticeSave(NoticeInput noticeInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        noticeCommandService.saveNotice(noticeInput);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

    /**
     * FIXME: 공지를 시간 순으로 정렬하지 않아도 되나?
     * @return
     */
    @GetMapping("/notice/{clubId}")
    public BaseResponse<List<NoticeOutput>> noticeSave(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(noticeQueryService.findNotice(clubId), ResponseCode.OK);
    }
}
