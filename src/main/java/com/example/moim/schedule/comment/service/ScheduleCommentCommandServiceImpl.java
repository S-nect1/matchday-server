package com.example.moim.schedule.comment.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.comment.dto.CommentOutput;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.comment.repository.CommentRepository;
import com.example.moim.schedule.comment.dto.CommentInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCommentCommandServiceImpl implements ScheduleCommentCommandService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserClubRepository userClubRepository;

    public CommentOutput saveComment(CommentInput commentInput, Long scheduleId, User user) {
        Schedule schedule = getSchedule(scheduleId);

        getUserClub(schedule.getClub(), user); // 가입된 회원인지 확인하기 위해 필요한 것

        Comment savedComment = commentRepository.save(Comment.createComment(user, schedule, commentInput.getContents()));

        return new CommentOutput(savedComment);
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }

    private UserClub getUserClub(Club club, User user) {
        return userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));
    }

}
