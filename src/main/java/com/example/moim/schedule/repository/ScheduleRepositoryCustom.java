package com.example.moim.schedule.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findByClubAndTime(Club club, LocalDateTime startTime, LocalDateTime endTime, String search, String category);

    Schedule findScheduleById(Long id);
}
