package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findByClubAndTime(Club club, LocalDateTime startTime, LocalDateTime endTime);
}
