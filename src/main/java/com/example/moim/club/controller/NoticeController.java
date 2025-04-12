package com.example.moim.club.controller;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.service.NoticeCommandService;
import com.example.moim.club.service.NoticeQueryService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {
    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;
    private final UserRepository userRepository;

    @PostMapping("/notice/{clubId}")
        public BaseResponse<NoticeOutput> saveNotice(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute NoticeInput noticeInput, @PathVariable("clubId") Long clubId) {
//    public BaseResponse<NoticeOutput> saveNotice(@ModelAttribute NoticeInput noticeInput, @PathVariable("clubId") Long clubId) {
        /**
         * FIXME: 컨트롤러 테스트용 코드
         */
//        User user = userRepository.findById(3L).get();
        NoticeOutput noticeOutput = noticeCommandService.saveNotice(userDetailsImpl.getUser(), noticeInput, clubId);
        return BaseResponse.onSuccess(noticeOutput, ResponseCode.OK);
    }

    @GetMapping("/notice/{clubId}")
        public BaseResponse<Slice<NoticeOutput>> findNotices(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam("cursorId") Long cursorId) {
//    public BaseResponse<Slice<NoticeOutput>> findNotices(@PathVariable Long clubId, @RequestParam(value = "cursorId", required = false) Long cursorId) {
        /**
         * FIXME: 컨트롤러 테스트용 코드
         */
//        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(noticeQueryService.findNotice(userDetailsImpl.getUser(), clubId, cursorId), ResponseCode.OK);
    }
}
