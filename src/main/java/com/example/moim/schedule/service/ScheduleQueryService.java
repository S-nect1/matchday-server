package com.example.moim.schedule.service;

import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchMonthInput;
import com.example.moim.user.entity.User;

import java.util.List;

public interface ScheduleQueryService {
    List<ScheduleOutput> findMonthlySchedulesWithFilter(ScheduleSearchMonthInput scheduleSearchMonthInput, User user);
    List<ScheduleOutput> findScheduleByDay(ScheduleSearchMonthInput scheduleSearchMonthInput, User user);
    ScheduleDetailOutput findScheduleDetail(Long scheduleId, User user);
}
