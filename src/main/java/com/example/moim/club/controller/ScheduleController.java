package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
import com.example.moim.club.service.ScheduleService;
import com.example.moim.user.dto.userDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs{
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ScheduleOutput scheduleSave(@RequestBody ScheduleInput scheduleInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return scheduleService.saveSchedule(scheduleInput, userDetailsImpl.getUser());
    }

    @GetMapping("/schedule")
    public List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findSchedule(scheduleSearchInput.getDate(), scheduleSearchInput.getClubId());
    }

    @GetMapping("/schedule/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleService.findScheduleDetail(id);
    }

    @PatchMapping("/schedule/vote")
    public void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput) {
        scheduleService.voteSchedule(scheduleVoteInput);
    }

    @PostMapping("/schedule/comment")
    public void scheduleComment(@RequestBody CommentInput commentInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        scheduleService.saveComment(commentInput, userDetailsImpl.getUser());
    }
}
