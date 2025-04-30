package com.example.moim.schedule.vote.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.vote.entity.AttendanceType;
import com.example.moim.schedule.vote.entity.ScheduleVote;
import com.example.moim.schedule.vote.repository.ScheduleVoteRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleVoteServiceImpl implements ScheduleVoteService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleVoteRepository scheduleVoteRepository;
    private final UserClubRepository userClubRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = getSchedule(scheduleVoteInput.getId());
        Optional<ScheduleVote> originalScheduleVote = scheduleVoteRepository.findByScheduleAndUser(schedule, user);

        boolean isAttendance = AttendanceType.fromKoreanName(scheduleVoteInput.getAttendance()).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.INVALID_ATTENDANCE_TYPE)).getIsAttendance();

        // 회원인지 확인
        getUserClub(schedule.getClub(), user);

        //투표 처음이면
        if (originalScheduleVote.isEmpty()) {
            scheduleVoteRepository.save(ScheduleVote.createScheduleVote(user, schedule, isAttendance));
            schedule.vote(scheduleVoteInput.getAttendance());
        } else { // 재투표인 경우
            schedule.reVote(originalScheduleVote.get().getIsAttendance(), isAttendance);
            originalScheduleVote.get().changeAttendance(isAttendance);
        }

        return schedule.getTitle() + "에 투표를 완료했습니다.";
    }

    public String voteEncourage(Long id, User user) {
        Schedule schedule = scheduleRepository.findWithClubById(id);

        // 운영진인지 검증
        validateClubStaff(getUserClub(schedule.getClub(), user));

        List<User> userList = userClubRepository.findUserByClub(schedule.getClub()).stream().map(UserClub::getUser).toList();
        eventPublisher.publishEvent(new ScheduleEncourageEvent(schedule, userList));

        return schedule.getTitle() + "의 투표 독려 알림을 보냈습니다.";
    }

    @Transactional
    public String closeScheduleVote(Long id, User user) {
        Schedule schedule = scheduleRepository.findWithClubById(id);

        // 운영진인지 검증
        validateClubStaff(getUserClub(schedule.getClub(), user));

        schedule.close();

        return schedule.getTitle() + "의 투표를 마감했습니다.";
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }

    private UserClub getUserClub(Club club, User user) {
        return userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
    }

    private void validateClubStaff(UserClub userClub) {
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ScheduleControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }
    }
}
