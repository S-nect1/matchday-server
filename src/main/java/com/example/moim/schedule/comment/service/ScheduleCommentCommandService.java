package com.example.moim.schedule.comment.service;

import com.example.moim.schedule.comment.dto.CommentInput;
import com.example.moim.schedule.comment.dto.CommentOutput;
import com.example.moim.user.entity.User;

public interface ScheduleCommentCommandService {
    CommentOutput saveComment(CommentInput commentInput, Long scheduleId, User user);
}
