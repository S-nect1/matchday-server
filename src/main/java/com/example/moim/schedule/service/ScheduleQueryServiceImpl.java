package com.example.moim.schedule.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.schedule.comment.dto.CommentOutput;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchMonthInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleCategory;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ScheduleOutput> findMonthlySchedulesWithFilter(ScheduleSearchMonthInput scheduleSearchMonthInput, User user) {
        Club club = getClub(scheduleSearchMonthInput.getClubId());

        getUserClub(club, user); // 권한 확인

        int year = scheduleSearchMonthInput.getDate() / 100;
        int month = scheduleSearchMonthInput.getDate() % 100;

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0).minusDays(6);
        LocalDateTime endDate = LocalDateTime.of(year, month, Month.of(month).minLength(), 23, 59, 59).plusDays(6);

        return scheduleRepository.findByClubAndTime(club,
                        startDate, endDate,
                        scheduleSearchMonthInput.getSearch(),
                        scheduleSearchMonthInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public List<ScheduleOutput> findScheduleByDay(ScheduleSearchMonthInput scheduleSearchMonthInput, User user) {
        int year = scheduleSearchMonthInput.getDate() / 10000;
        int month = (scheduleSearchMonthInput.getDate() / 100) % 100;
        int day = scheduleSearchMonthInput.getDate() % 100;

        LocalDateTime searchDate = LocalDateTime.of(year, month, day, 0, 0, 0);
        Club club = getClub(scheduleSearchMonthInput.getClubId());

        getUserClub(club, user); // 권한 확인

        return scheduleRepository.findByClubAndTime(club, searchDate, searchDate.plusDays(1), scheduleSearchMonthInput.getSearch(), scheduleSearchMonthInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    /**
     * FIXME: 세부 일정 페이지 보고 데이터 넘겨주기. 댓글도 포함되어야 함?
     * @param scheduleId
     * @return
     */
    @Transactional
    public ScheduleDetailOutput findScheduleDetail(Long scheduleId, User user) {
        Schedule schedule = scheduleRepository.findByIdWithComment(scheduleId);

        // 조회 수 증가 반영
        schedule.increaseViewCount();

        getUserClub(schedule.getClub(), user); // 권한 확인

        // 댓글 정보도 함께 포함
        List<CommentOutput> comments = schedule.getComments().stream().map(CommentOutput::new).toList();

        if (schedule.getCategory().equals(ScheduleCategory.TOURNAMENT) || schedule.getCategory().equals(ScheduleCategory.FRIENDLY_MATCH)) {
            // 상대팀 정보 받아오는 로직 추가
        }

        return new ScheduleDetailOutput(schedule, comments);
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
