package com.example.moim.schedule.controller;

import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.service.ScheduleService;
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

    @PostMapping(value = "/schedules")
    public ScheduleOutput scheduleSave(@RequestBody @Valid ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleService.saveSchedule(scheduleInput, userDetailsImpl.getUser());
    }

    @PatchMapping("/schedules/{id}")
    public ScheduleOutput scheduleUpdate(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleService.updateSchedule(scheduleUpdateInput, id, userDetailsImpl.getUser());
    }

    @GetMapping(value = "/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findMonthSchedule(scheduleSearchInput);
    }

    @GetMapping(value = "/schedules/day", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> dayScheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findDaySchedule(scheduleSearchInput);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleService.findScheduleDetail(id);
    }

    @DeleteMapping("/schedules/{id}")
    public void scheduleDelete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @PatchMapping("/schedules/vote")
    public void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.voteSchedule(scheduleVoteInput, userDetailsImpl.getUser());
    }

    @PostMapping("/schedules/encourage/{id}")
    public void voteEncourage(@PathVariable Long id) {
        scheduleService.voteEncourage(id);
    }

    @PatchMapping("/schedules/close/{id}")
    public void scheduleClose(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.closeSchedule(id, userDetailsImpl.getUser());
    }

    @PostMapping("/schedules/{id}/comments")
    public void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.saveComment(commentInput, userDetailsImpl.getUser());
    }
}
