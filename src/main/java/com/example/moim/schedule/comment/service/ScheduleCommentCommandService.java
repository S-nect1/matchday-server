package com.example.moim.schedule.comment.service;

import com.example.moim.schedule.dto.CommentInput;
import com.example.moim.user.entity.User;

public interface ScheduleCommentCommandService {
    void saveComment(CommentInput commentInput, User user);
}
