package com.example.moim.club.controller;

import com.example.moim.club.dto.ScheduleDetailOutput;
import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleOutput;
import com.example.moim.club.dto.ScheduleSearchInput;
import com.example.moim.club.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ScheduleOutput scheduleSave(@RequestBody ScheduleInput scheduleInput) {
        return scheduleService.saveSchedule(scheduleInput);
    }

    @GetMapping("/schedule")
    public List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput) {
        return scheduleService.findSchedule(scheduleSearchInput.getDate(), scheduleSearchInput.getClubId());
    }

    @GetMapping("/schedule/{id}")
    public ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id) {
        return scheduleService.findScheduleDetail(id);
    }

}
