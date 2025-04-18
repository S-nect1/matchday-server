package com.example.moim.schedule.comment.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.comment.dto.CommentOutput;
import com.example.moim.schedule.comment.service.ScheduleCommentCommandService;
import com.example.moim.schedule.comment.dto.CommentInput;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleCommentController {

    private final ScheduleCommentCommandService scheduleCommentCommandService;

    /**
     * FIXME: 컨트롤러 수동테스트를 위해 필요한 코드, 추후에 지울 것.
     */
    private final UserRepository userRepository;

    @PostMapping("/schedules/{id}/comments")
    public BaseResponse<CommentOutput> scheduleComment(@RequestBody @Valid CommentInput commentInput, @PathVariable("id") Long scheduleId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        /**
         * FIXME: 컨트롤러 수동테스트를 위해 필요한 코드, 추후에 지울 것.
         */
//    public BaseResponse<CommentOutput> scheduleComment(@RequestBody @Valid CommentInput commentInput, @PathVariable("id") Long scheduleId) {
//        User user = userRepository.findById(3L).get();
        CommentOutput commentOutput = scheduleCommentCommandService.saveComment(commentInput, scheduleId, userDetailsImpl.getUser());
        return BaseResponse.onSuccess(commentOutput, ResponseCode.OK);
    }

}
