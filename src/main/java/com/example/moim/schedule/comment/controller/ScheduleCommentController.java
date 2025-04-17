package com.example.moim.schedule.comment.controller;

import com.example.moim.schedule.comment.service.ScheduleCommentCommandService;
import com.example.moim.schedule.dto.CommentInput;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleCommentController {

    private final ScheduleCommentCommandService scheduleCommentCommandService;

    @PostMapping("/schedules/{id}/comments")
    public void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleCommentCommandService.saveComment(commentInput, userDetailsImpl.getUser());
    }
}
