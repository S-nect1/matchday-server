package com.example.moim.club.service;

import com.example.moim.club.dto.*;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.*;
import com.example.moim.exception.club.ClubPermissionException;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.notification.dto.ScheduleVoteEvent;
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

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(scheduleInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new ClubPermissionException("모임 일정을 만들 권한이 없습니다.");
        }

        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(clubRepository.findById(scheduleInput.getClubId()).get(), scheduleInput));
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

    public List<ScheduleOutput> findSchedule(ScheduleSearchInput scheduleSearchInput) {
        return scheduleRepository.findByClubAndTime(clubRepository.findById(scheduleSearchInput.getClubId()).get(),
                LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, 1, 0, 0, 0).minusDays(6),
                LocalDateTime.of(scheduleSearchInput.getDate() / 100, scheduleSearchInput.getDate() % 100, Month.of(scheduleSearchInput.getDate() % 100).minLength(), 23, 59, 59).plusDays(6),
                scheduleSearchInput.getSearch(),
                scheduleSearchInput.getCategory())
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public ScheduleDetailOutput findScheduleDetail(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        return new ScheduleDetailOutput(schedule, scheduleVoteRepository.findBySchedule(schedule).stream()
                .map(scheduleVote -> new ScheduleUserOutput(scheduleVote.getUser().getName(), scheduleVote.getUser().getImgPath())).toList());

    }

    @Transactional
    public void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleVoteInput.getId());
        Optional<ScheduleVote> originalScheduleVote = scheduleVoteRepository.findByScheduleAndUser(schedule, user);
        if (originalScheduleVote.isEmpty()) {
            scheduleVoteRepository.save(ScheduleVote.createScheduleVote(user, schedule, scheduleVoteInput.getAttendance()));
            schedule.vote(scheduleVoteInput.getAttendance());
        } else {
            schedule.reVote(originalScheduleVote.get().getAttendance(), scheduleVoteInput.getAttendance());
            originalScheduleVote.get().changeAttendance(scheduleVoteInput.getAttendance());
        }
//        if (scheduleVoteInput.getAttendance().equals("attend")) {
//            eventPublisher.publishEvent(new ScheduleVoteEvent(schedule, user));
//        }
    }

    public void voteEncourage(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        List<User> userList = userClubRepository.findUserByClub(schedule.getClub());
        eventPublisher.publishEvent(new ScheduleEncourageEvent(schedule, userList));
    }

    public void saveComment(CommentInput commentInput, User user) {
        commentRepository.save(Comment.createComment(user, scheduleRepository.findById(commentInput.getId()).get(), commentInput.getContents()));
    }
}
