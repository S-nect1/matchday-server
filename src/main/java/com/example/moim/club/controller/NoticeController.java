package com.example.moim.club.controller;

import com.example.moim.club.dto.NoticeInput;
import com.example.moim.club.dto.NoticeOutput;
import com.example.moim.club.service.NoticeService;
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
    private final NoticeService noticeService;

    /**
     * FIXME: 응답 값이 무조건 있어야 함
     * @param noticeInput
     * @param userDetailsImpl
     */
    @PostMapping("/notice")
    public void noticeSave(NoticeInput noticeInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        noticeService.saveNotice(noticeInput);
    }

    /**
     * FIXME: 공지를 시간 순으로 정렬하지 않아도 되나?
     * @param clubId
     * @param userDetailsImpl
     * @return
     */
    @GetMapping("/notice/{clubId}")
    public List<NoticeOutput> noticeSave(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return noticeService.findNotice(clubId);
    }
}
