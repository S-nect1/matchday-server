package com.example.moim.schedule.service;

import com.example.moim.schedule.dto.*;
import com.example.moim.user.entity.User;

public interface ScheduleCommandService {
    ScheduleOutput saveSchedule(ScheduleInput scheduleInput, User user);
    ScheduleOutput updateSchedule(ScheduleUpdateInput scheduleUpdateInput, Long id, User user);
    void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user);
    void deleteSchedule(Long id);
    void voteEncourage(Long id);
    void closeSchedule(Long id, User user);
    void saveComment(CommentInput commentInput, User user);

}
