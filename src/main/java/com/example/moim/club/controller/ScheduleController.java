package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
import com.example.moim.club.service.ScheduleService;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs{
    private final ScheduleService scheduleService;

    @PostMapping(value = "/schedule")
    public ScheduleOutput scheduleSave(@RequestBody @Valid ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleService.saveSchedule(scheduleInput, userDetailsImpl.getUser());
    }

    @PatchMapping("/schedule")
    public ScheduleOutput scheduleUpdate(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleService.updateSchedule(scheduleUpdateInput, userDetailsImpl.getUser());
    }

    @GetMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findSchedule(scheduleSearchInput);
    }

    @GetMapping("/schedule/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleService.findScheduleDetail(id);
    }

    @PatchMapping("/schedule/vote")
    public void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.voteSchedule(scheduleVoteInput, userDetailsImpl.getUser());
    }

    @PostMapping("/schedule/comment")
    public void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.saveComment(commentInput, userDetailsImpl.getUser());
    }
}
