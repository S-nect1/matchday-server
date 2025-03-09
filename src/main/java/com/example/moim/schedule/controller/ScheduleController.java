package com.example.moim.schedule.controller;

import com.example.moim.schedule.service.ScheduleService;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;
import com.example.moim.schedule.dto.ScheduleUpdateInput;
import com.example.moim.schedule.dto.ScheduleVoteInput;
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

    @GetMapping(value = "/schedule/day", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> dayScheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findDaySchedule(scheduleSearchInput);
    }

    @GetMapping("/schedule/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleService.findScheduleDetail(id);
    }

    @DeleteMapping("/schedule/{id}")
    public void scheduleDelete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @PatchMapping("/schedule/vote")
    public void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.voteSchedule(scheduleVoteInput, userDetailsImpl.getUser());
    }

    @PostMapping("/schedule/encourage/{id}")
    public void voteEncourage(@PathVariable Long id) {
        scheduleService.voteEncourage(id);
    }

    @PatchMapping("/schedule/close/{id}")
    public void scheduleClose(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleService.closeSchedule(id, userDetailsImpl.getUser());
    }

//    @PostMapping("/schedule/comment")
//    public void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
//        scheduleService.saveComment(commentInput, userDetailsImpl.getUser());
//    }
}
