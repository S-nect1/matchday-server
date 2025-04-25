package com.example.moim.schedule.vote.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.schedule.vote.service.ScheduleVoteService;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleVoteController implements ScheduleVoteControllerDocs {

    private final ScheduleVoteService scheduleVoteService;

    @PatchMapping("/schedules/vote")
    public BaseResponse<String> createScheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String result = scheduleVoteService.voteSchedule(scheduleVoteInput, userDetailsImpl.getUser());

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }

    @PostMapping("/schedules/encourage/{id}")
    public BaseResponse<String> encourageVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String result = scheduleVoteService.voteEncourage(id, userDetailsImpl.getUser());

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }

    @PatchMapping("/schedules/close/{id}")
    public BaseResponse<String> closeScheduleVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String result = scheduleVoteService.closeScheduleVote(id, userDetailsImpl.getUser());

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }
}
