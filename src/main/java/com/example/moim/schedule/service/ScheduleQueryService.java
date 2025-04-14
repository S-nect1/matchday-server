package com.example.moim.schedule.service;

import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;

import java.util.List;

public interface ScheduleQueryService {
    List<ScheduleOutput> findMonthSchedule(ScheduleSearchInput scheduleSearchInput);
    List<ScheduleOutput> findDaySchedule(ScheduleSearchInput scheduleSearchInput);
    ScheduleDetailOutput findScheduleDetail(Long scheduleId);
}
