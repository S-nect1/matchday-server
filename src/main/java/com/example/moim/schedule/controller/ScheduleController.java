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
    public ScheduleOutput createSchedule(@RequestBody @Valid ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleCommandService.saveSchedule(scheduleInput, userDetailsImpl.getUser());
    }

    @PatchMapping("/schedules/{id}")
    public ScheduleOutput updateSchedule(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return scheduleCommandService.updateSchedule(scheduleUpdateInput, id, userDetailsImpl.getUser());
    }

    @GetMapping(value = "/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> getScheduleList(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleQueryService.findMonthlySchedulesWithFilter(scheduleSearchInput);
    }

    @GetMapping(value = "/schedules/day", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleOutput> getScheduleListByDay(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleQueryService.findScheduleByDay(scheduleSearchInput);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleDetailOutput getScheduleDetail(@PathVariable Long id) {
        return scheduleQueryService.findScheduleDetail(id);
    }

    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleCommandService.deleteSchedule(id);
    }
}
