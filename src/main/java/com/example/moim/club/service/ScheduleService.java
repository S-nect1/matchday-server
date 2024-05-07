package com.example.moim.club.service;

import com.example.moim.club.dto.*;
import com.example.moim.club.entity.Comment;
import com.example.moim.club.entity.Schedule;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.CommentRepository;
import com.example.moim.club.repository.ScheduleRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ClubRepository clubRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user) {
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(clubRepository.findById(scheduleInput.getClubId()).get(), scheduleInput));
        eventPublisher.publishEvent(new ScheduleSaveEvent(schedule, user));
        return new ScheduleOutput(schedule);
    }

    @Transactional
    public ScheduleOutput updateSchedule(ScheduleUpdateInput scheduleUpdateInput) {
        Schedule schedule = scheduleRepository.findById(scheduleUpdateInput.getId()).get();
        schedule.updateSchedule(scheduleUpdateInput);
        return new ScheduleOutput(schedule);
    }

    public List<ScheduleOutput> findSchedule(Integer date, Long clubId) {
        return scheduleRepository.findByClubAndTime(clubRepository.findById(clubId).get(),
                LocalDateTime.of(date / 100, date % 100, 1, 0, 0, 0).minusDays(6),
                LocalDateTime.of(date / 100, date % 100, Month.of(date % 100).minLength(), 23, 59, 59).plusDays(6))
                .stream().map(ScheduleOutput::new).collect(Collectors.toList());
    }

    public ScheduleDetailOutput findScheduleDetail(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        return new ScheduleDetailOutput(schedule, commentRepository.findBySchedule(schedule).stream().map(CommentOutput::new).collect(Collectors.toList()));

    }

    @Transactional
    public void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleVoteInput.getId());
        schedule.vote(scheduleVoteInput.getAttendance());
        if (scheduleVoteInput.getAttendance().equals("attend")) {
            eventPublisher.publishEvent(new ScheduleVoteEvent(schedule, user));
        }
    }

    public void saveComment(CommentInput commentInput, User user) {
        commentRepository.save(Comment.createComment(user, scheduleRepository.findById(commentInput.getId()).get(), commentInput.getContents()));
    }
}
