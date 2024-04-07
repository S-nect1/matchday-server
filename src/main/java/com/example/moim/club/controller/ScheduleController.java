package com.example.moim.club.controller;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleOutput;
import com.example.moim.club.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ScheduleOutput scheduleSave(ScheduleInput scheduleInput) {
        return scheduleService.saveSchedule(scheduleInput);
    }

    @GetMapping("/schedule/{date}")
    public List<ScheduleOutput> scheduleFind(@PathVariable Integer date, @RequestParam Long clubId) {
        return scheduleService.findSchedule(date, clubId);
    }
}
