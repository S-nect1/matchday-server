package com.example.moim.schedule.vote.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ScheduleVoteControllerDocs {
    @Operation(summary = "일정 참가 투표", description = "참가면 attendance = attend, 참가 취소는 absent")
    BaseResponse<String> createScheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 참가 투표 독려")
    BaseResponse<String> encourageVote(@PathVariable Long id);

    @Operation(summary = "일정 참가 투표 마감")
    BaseResponse<String> closeScheduleVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

}
