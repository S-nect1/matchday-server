package com.example.moim.club.service;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleOutput;
import com.example.moim.club.entity.Schedule;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ClubRepository clubRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput) {
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(clubRepository.findById(scheduleInput.getClubId()).get(), scheduleInput));
        return new ScheduleOutput(schedule);
    }

    public List<ScheduleOutput> findSchedule(Integer date, Long clubId) {
        return scheduleRepository.findByClubAndTime(clubRepository.findById(clubId).get(),
                LocalDateTime.of(date / 100, date % 100, 1, 0, 0, 0).minusDays(6),
                LocalDateTime.of(date / 100, date % 100, Month.of(date % 100).minLength(), 23, 59, 59).plusDays(6))
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());

    }
}
