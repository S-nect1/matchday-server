package com.example.moim.schedule.vote.controller;

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
    public void createScheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleVoteService.voteSchedule(scheduleVoteInput, userDetailsImpl.getUser());
    }

    @PostMapping("/schedules/encourage/{id}")
    public void encourageVote(@PathVariable Long id) {
        scheduleVoteService.voteEncourage(id);
    }

    @PatchMapping("/schedules/close/{id}")
    public void closeScheduleVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleVoteService.closeScheduleVote(id, userDetailsImpl.getUser());
    }
}
