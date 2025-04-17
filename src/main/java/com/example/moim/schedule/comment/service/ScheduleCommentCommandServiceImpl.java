package com.example.moim.schedule.comment.service;

import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.comment.repository.CommentRepository;
import com.example.moim.schedule.dto.CommentInput;
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

    public void saveComment(CommentInput commentInput, User user) {
        Schedule schedule = getSchedule(commentInput.getId());
        commentRepository.save(Comment.createComment(user, schedule, commentInput.getContents()));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleControllerAdvice(ResponseCode.SCHEDULE_NOT_FOUND));
    }

}
