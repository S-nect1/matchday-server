package com.example.moim.schedule.service;

import com.example.moim.club.entity.*;
import com.example.moim.club.repository.*;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.vote.entity.ScheduleVote;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.comment.repository.CommentRepository;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.vote.repository.ScheduleVoteRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ClubRepository clubRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserClubRepository userClubRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MatchApplicationRepository matchApplicationRepository;

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user) {
        /**
         * TODO: 친선 매치일 경우 상대방 팀에도 일정 생성되도록 처리해야 하나?
         */
        Club club = getClub(scheduleInput.getClubId());

        validateClubStaff(getUserClub(club, user));

        Schedule schedule = scheduleRepository.save(Schedule.from(club, scheduleInput));

        /**
         * TODO: 알림 리팩터링 버전으로 다시 적용해야 함
         */
        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));

        return new ScheduleOutput(schedule);
    }

    @Transactional
    public ScheduleOutput updateSchedule(ScheduleUpdateInput scheduleUpdateInput, Long id, User user) {
        /**
         * TODO: 친선 매치일 경우 상대방 팀에도 일정 수정 반영해야 하나?
         */
        Club club = getClub(scheduleUpdateInput.getClubId());

        validateClubStaff(getUserClub(club, user));

        Schedule schedule = getSchedule(id);

        schedule.update(scheduleUpdateInput);

        /**
         * TODO: 알림 리팩터링 버전으로 다시 적용해야 함
         */
        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));

        return new ScheduleOutput(schedule);
    }

    /**
     * TODO: 친선 매치라면 같이 삭제해주는 로직 필요
     * @param id
     */
    public String deleteSchedule(Long id, User user) {

        Schedule schedule = getSchedule(id);

        // 운영진인지 권한 확인
        validateClubStaff(getUserClub(schedule.getClub(), user));

        scheduleRepository.deleteById(id);

        return "스케줄을 정상적으로 취소하였습니다.";
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
    }

    private UserClub getUserClub(Club club, User user) {
        return userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }

    private void validateClubStaff(UserClub userClub) {
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ScheduleControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }
    }
}
