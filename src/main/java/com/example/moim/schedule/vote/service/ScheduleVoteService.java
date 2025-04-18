package com.example.moim.schedule.vote.service;

import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.user.entity.User;

public interface ScheduleVoteService {
    void voteSchedule(ScheduleVoteInput scheduleVoteInput, User user);
    void voteEncourage(Long id);
    void closeScheduleVote(Long id, User user);

}
