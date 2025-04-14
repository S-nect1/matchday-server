package com.example.moim.schedule.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.dto.MatchApplyClubOutput;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final ClubRepository clubRepository;
    private final MatchApplicationRepository matchApplicationRepository;

    public List<ScheduleOutput> findMonthSchedule(ScheduleSearchInput scheduleSearchInput) {
        Club club = getClub(scheduleSearchInput.getClubId());

        return scheduleRepository.findByClubAndTime(club,
                        LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, 1, 0, 0, 0).minusDays(6),
                        LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, Month.of(scheduleSearchInput.getDate() % 100).minLength(), 23, 59, 59).plusDays(6),
                        scheduleSearchInput.getSearch(),
                        scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public List<ScheduleOutput> findDaySchedule(ScheduleSearchInput scheduleSearchInput) {
        LocalDateTime searchDate = LocalDateTime.of(scheduleSearchInput.getDate() / 10000, (scheduleSearchInput.getDate() / 100) % 100, scheduleSearchInput.getDate() % 100,
                0, 0, 0);
        Club club = getClub(scheduleSearchInput.getClubId());

        return scheduleRepository.findByClubAndTime(club,
                        searchDate, searchDate.plusDays(1), scheduleSearchInput.getSearch(), scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public ScheduleDetailOutput findScheduleDetail(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);

        return new ScheduleDetailOutput(schedule,
//                scheduleVoteRepository.findBySchedule(schedule).stream().map(ScheduleUserOutput::new).toList(),
                matchApplicationRepository.findBySchedule(schedule).stream().map(MatchApplyClubOutput::new).toList());
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }
}
