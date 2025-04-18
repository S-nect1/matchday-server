package com.example.moim.schedule.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.dto.MatchApplyClubOutput;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;
    private final MatchApplicationRepository matchApplicationRepository;

    /**
     * TODO: 기획에 맞게, 스케줄 조회 기준 바꾸기
     * User 가 가진 스케줄로 조회해야하는거 아닌가..?
     * 그럼 이거 구조 아예 다시 바꿔야 할 것 같은데...
     * @param scheduleSearchInput
     * @return
     */
    public List<ScheduleOutput> findMonthlySchedulesWithFilter(ScheduleSearchInput scheduleSearchInput) {
        Club club = getClub(scheduleSearchInput.getClubId());

        int year = scheduleSearchInput.getDate() / 100;
        log.info("year : {}", year);
        int month = scheduleSearchInput.getDate() % 100;
        log.info("month : {}", month);

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0).minusDays(6);
        LocalDateTime endDate = LocalDateTime.of(year, month, Month.of(month).minLength(), 23, 59, 59).plusDays(6);

        return scheduleRepository.findByClubAndTime(club,
                        startDate, endDate,
                        scheduleSearchInput.getSearch(),
                        scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public List<ScheduleOutput> findScheduleByDay(ScheduleSearchInput scheduleSearchInput) {
        LocalDateTime searchDate = LocalDateTime.of(scheduleSearchInput.getDate() / 10000, (scheduleSearchInput.getDate() / 100) % 100, scheduleSearchInput.getDate() % 100,
                0, 0, 0);
        Club club = getClub(scheduleSearchInput.getClubId());

        return scheduleRepository.findByClubAndTime(club,
                        searchDate, searchDate.plusDays(1), scheduleSearchInput.getSearch(), scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    /**
     * FIXME: 세부 일정 페이지 보고 데이터 넘겨주기. 댓글도 포함되어야 함
     * @param scheduleId
     * @return
     */
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

    private UserClub getUserClub(Club club, User user) {
        return userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
    }
}
