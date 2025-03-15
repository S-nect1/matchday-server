package com.example.moim.schedule.service;

import com.example.moim.club.entity.*;
import com.example.moim.club.repository.*;
import com.example.moim.club.exception.ClubPermissionException;
import com.example.moim.match.dto.MatchApplyClubOutput;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;
import com.example.moim.schedule.dto.ScheduleUpdateInput;
import com.example.moim.schedule.dto.ScheduleVoteInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import com.example.moim.schedule.repository.CommentRepository;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.repository.ScheduleVoteRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ClubRepository clubRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserClubRepository userClubRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ScheduleVoteRepository scheduleVoteRepository;
    private final MatchApplicationRepository matchApplicationRepository;

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(scheduleInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new ClubPermissionException("모임 일정을 만들 권한이 없습니다.");
        }

        Schedule schedule = scheduleRepository.save(
                Schedule.createSchedule(clubRepository.findById(scheduleInput.getClubId()).get(), scheduleInput));

        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));
        return new ScheduleOutput(schedule);
    }

    @Transactional
    public ScheduleOutput updateSchedule(ScheduleUpdateInput scheduleUpdateInput, User user) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(scheduleUpdateInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new ClubPermissionException("모임 일정을 수정할 권한이 없습니다.");
        }

        Schedule schedule = scheduleRepository.findById(scheduleUpdateInput.getId()).get();
        schedule.updateSchedule(scheduleUpdateInput);
        return new ScheduleOutput(schedule);
    }

    /**
     * TODO: 이름 명확하게 변경하기. findMontSchedule 등으로
     * @param scheduleSearchInput
     * @return
     */
    public List<ScheduleOutput> findSchedule(ScheduleSearchInput scheduleSearchInput) {
        return scheduleRepository.findByClubAndTime(clubRepository.findById(scheduleSearchInput.getClubId()).get(),
                        LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, 1, 0, 0, 0).minusDays(6),
                        LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, Month.of(scheduleSearchInput.getDate() % 100).minLength(), 23, 59, 59).plusDays(6),
                        scheduleSearchInput.getSearch(),
                        scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public List<ScheduleOutput> findDaySchedule(ScheduleSearchInput scheduleSearchInput) {
        LocalDateTime searchDate = LocalDateTime.of(scheduleSearchInput.getDate() / 10000, (scheduleSearchInput.getDate() / 100) % 100, scheduleSearchInput.getDate() % 100,
                0, 0, 0);
        return scheduleRepository.findByClubAndTime(clubRepository.findById(scheduleSearchInput.getClubId()).get(),
                        searchDate, searchDate.plusDays(1), scheduleSearchInput.getSearch(), scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public ScheduleDetailOutput findScheduleDetail(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        return new ScheduleDetailOutput(schedule,
//                scheduleVoteRepository.findBySchedule(schedule).stream().map(ScheduleUserOutput::new).toList(),
                matchApplicationRepository.findBySchedule(schedule).stream().map(MatchApplyClubOutput::new).toList());
    }

    /**
     * TODO: void -> 기본 응답
     * @param scheduleVoteInput
     * @param user
     */
    @Transactional
    public void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleVoteInput.getId());
        Optional<ScheduleVote> originalScheduleVote = scheduleVoteRepository.findByScheduleAndUser(schedule, user);
        //투표 처음이면
        if (originalScheduleVote.isEmpty()) {
            scheduleVoteRepository.save(ScheduleVote.createScheduleVote(user, schedule, scheduleVoteInput.getAttendance()));
            schedule.vote(scheduleVoteInput.getAttendance());
        } else {// 재투표인 경우
            schedule.reVote(originalScheduleVote.get().getAttendance(), scheduleVoteInput.getAttendance());
            originalScheduleVote.get().changeAttendance(scheduleVoteInput.getAttendance());
        }
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
        Schedule schedule = scheduleRepository.findScheduleById(id);
        List<User> userList = userClubRepository.findUserByClub(schedule.getClub()).stream().map(UserClub::getUser).toList();
        eventPublisher.publishEvent(new ScheduleEncourageEvent(schedule, userList));
    }

    /**
     * TODO: void -> 기본 응답, 이름 명확하게 바꾸기 closeScheduleVote 등
     * @param id
     */
    @Transactional
    public void closeSchedule(Long id, User user) {
        Schedule schedule = scheduleRepository.findById(id).get();
        UserClub userClub = userClubRepository.findByClubAndUser(schedule.getClub(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new ClubPermissionException("일정 투표를 마감할 권한이 없습니다.");
        }

        schedule.closeSchedule();
    }

//    public void saveComment(CommentInput commentInput, User user) {
//        commentRepository.save(Comment.createComment(user, scheduleRepository.findById(commentInput.getId()).get(), commentInput.getContents()));
//    }
}
