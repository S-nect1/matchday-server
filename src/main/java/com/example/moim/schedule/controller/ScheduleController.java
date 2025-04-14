package com.example.moim.schedule.controller;

import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.service.ScheduleCommandService;
import com.example.moim.schedule.service.ScheduleQueryService;
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
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    @PostMapping(value = "/schedules")
    public ScheduleOutput scheduleSave(@RequestBody @Valid ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleCommandService.saveSchedule(scheduleInput, userDetailsImpl.getUser());
    }

    @PatchMapping("/schedules/{id}")
    public ScheduleOutput scheduleUpdate(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleCommandService.updateSchedule(scheduleUpdateInput, id, userDetailsImpl.getUser());
    }

    @GetMapping(value = "/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleQueryService.findMonthSchedule(scheduleSearchInput);
    }

    @GetMapping(value = "/schedules/day", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> dayScheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleQueryService.findDaySchedule(scheduleSearchInput);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleQueryService.findScheduleDetail(id);
    }

    @DeleteMapping("/schedules/{id}")
    public void scheduleDelete(@PathVariable Long id) {
        scheduleCommandService.deleteSchedule(id);
    }

    @PatchMapping("/schedules/close/{id}")
    public void scheduleClose(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleCommandService.closeSchedule(id, userDetailsImpl.getUser());
    }

    @PostMapping("/schedules/{id}/comments")
    public void scheduleComment(@RequestBody @Valid CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        scheduleCommandService.saveComment(commentInput, userDetailsImpl.getUser());
    }
}
