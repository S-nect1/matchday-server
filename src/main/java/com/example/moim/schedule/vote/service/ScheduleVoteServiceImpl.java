package com.example.moim.schedule.vote.service;

import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.schedule.dto.ScheduleVoteInput;
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

    /**
     * TODO: void -> 기본 응답
     * @param scheduleVoteInput
     * @param user
     */
    @Transactional
    public void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = getSchedule(scheduleVoteInput.getId());
        Optional<ScheduleVote> originalScheduleVote = scheduleVoteRepository.findByScheduleAndUser(schedule, user);

        boolean isAttendance = AttendanceType.fromKoreanName(scheduleVoteInput.getAttendance()).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.INVALID_ATTENDANCE_TYPE)).getIsAttendance();

        //투표 처음이면
        if (originalScheduleVote.isEmpty()) {
            scheduleVoteRepository.save(ScheduleVote.createScheduleVote(user, schedule, isAttendance));
            schedule.vote(scheduleVoteInput.getAttendance());
        } else { // 재투표인 경우
            schedule.reVote(originalScheduleVote.get().getIsAttendance(), isAttendance);
            originalScheduleVote.get().changeAttendance(isAttendance);
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
    public void voteEncourage(Long id) {
        Schedule schedule = scheduleRepository.findWithClubById(id);
        List<User> userList = userClubRepository.findUserByClub(schedule.getClub()).stream().map(UserClub::getUser).toList();
        eventPublisher.publishEvent(new ScheduleEncourageEvent(schedule, userList));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }
}
