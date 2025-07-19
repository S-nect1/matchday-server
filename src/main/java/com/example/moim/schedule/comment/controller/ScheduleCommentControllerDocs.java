package com.example.moim.schedule.comment.controller;

import com.example.moim.schedule.comment.dto.CommentInput;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "일정 댓글 api")
public interface ScheduleCommentControllerDocs {
    @Operation(summary = "일정에 댓글 남기기")
//    void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
    void scheduleComment(@RequestBody @Valid CommentInput commentInput);
}
