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

    @PostMapping("/notice")
    public void noticeSave(NoticeInput noticeInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        noticeService.saveNotice(noticeInput);
    }

    @GetMapping("/notice/{clubId}")
    public List<NoticeOutput> noticeSave(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return noticeService.findNotice(clubId);
    }
}
