package com.example.moim.schedule.service;

import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;

import java.util.List;

public interface ScheduleQueryService {
    List<ScheduleOutput> findMonthlySchedulesWithFilter(ScheduleSearchInput scheduleSearchInput);
    List<ScheduleOutput> findScheduleByDay(ScheduleSearchInput scheduleSearchInput);
    ScheduleDetailOutput findScheduleDetail(Long scheduleId);
}
