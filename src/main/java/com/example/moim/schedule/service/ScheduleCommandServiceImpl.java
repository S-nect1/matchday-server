package com.example.moim.schedule.service;

import com.example.moim.club.entity.*;
import com.example.moim.club.repository.*;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.entity.Comment;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.CommentRepository;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.repository.ScheduleVoteRepository;
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
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ScheduleVoteRepository scheduleVoteRepository;
    private final MatchApplicationRepository matchApplicationRepository;

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user) {
        Club club = getClub(scheduleInput.getClubId());
        UserClub userClub = getUserClub(club, user);
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ScheduleControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        Schedule schedule = scheduleRepository.save(Schedule.from(club, scheduleInput));

        /**
         * TODO: 알림 리팩터링 버전으로 다시 적용해야 함
         */
        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));

        return new ScheduleOutput(schedule);
    }

    @Transactional
    public ScheduleOutput updateSchedule(ScheduleUpdateInput scheduleUpdateInput, Long id, User user) {
        Club club = getClub(scheduleUpdateInput.getClubId());
        UserClub userClub = getUserClub(club, user);

        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ScheduleControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        Schedule schedule = getSchedule(id);

        schedule.update(scheduleUpdateInput);

        /**
         * TODO: 알림 리팩터링 버전으로 다시 적용해야 함
         */
        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));

        return new ScheduleOutput(schedule);
    }

    /**
     * TODO: void -> 기본 응답
     * @param scheduleVoteInput
     * @param user
     */
    @Transactional
    public void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = getSchedule(scheduleVoteInput.getId());
        Optional<ScheduleVote> originalScheduleVote = scheduleVoteRepository.findByScheduleAndUser(schedule, user);

        //투표 처음이면
        if (originalScheduleVote.isEmpty()) {
            scheduleVoteRepository.save(ScheduleVote.createScheduleVote(user, schedule, scheduleVoteInput.getAttendance()));
            schedule.vote(scheduleVoteInput.getAttendance());
        } else { // 재투표인 경우
            schedule.reVote(originalScheduleVote.get().getAttendance(), scheduleVoteInput.getAttendance());
            originalScheduleVote.get().changeAttendance(scheduleVoteInput.getAttendance());
        }
        /**
         * TODO: 왜 알림 보내려고 했는지 확인하고 로직 추가하기
         */
//        if (scheduleVoteInput.getAttendance().equals("attend")) {
//            eventPublisher.publishEvent(new ScheduleVoteEvent(schedule, user));
//        }
    }

    /**
     * TODO: void -> 기본 응답
     * FIXME: 운영진인지 아닌지 체크하는 로직 필요함(필터에서 걸러주면 필요 X)
     * @param id
     */
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    /**
     * TODO: void -> 기본 응답
     * FIXME: 운영진인지 아닌지 체크하는 로직 필요함(필터에서 걸러주면 필요 X)
     * @param id
     */
    public void voteEncourage(Long id) {
        Schedule schedule = scheduleRepository.findWithClubById(id);
        List<User> userList = userClubRepository.findUserByClub(schedule.getClub()).stream().map(UserClub::getUser).toList();
        eventPublisher.publishEvent(new ScheduleEncourageEvent(schedule, userList));
    }

    /**
     * TODO: void -> 기본 응답, 이름 명확하게 바꾸기 closeScheduleVote 등
     * @param id
     */
    @Transactional
    public void closeSchedule(Long id, User user) {
        Schedule schedule = scheduleRepository.findWithClubById(id);
        UserClub userClub = getUserClub(schedule.getClub(), user);
        if (!(userClub.getClubRole().equals(ClubRole.STAFF))) {
            throw new ScheduleControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        schedule.close();
    }

    public void saveComment(CommentInput commentInput, User user) {
        Schedule schedule = getSchedule(commentInput.getId());
        commentRepository.save(Comment.createComment(user, schedule, commentInput.getContents()));
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
}
