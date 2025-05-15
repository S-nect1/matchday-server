package com.example.moim.schedule.vote.service;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.user.entity.User;

public interface ScheduleVoteService {
    String voteSchedule(ScheduleVoteInput scheduleVoteInput, Long id, User user);
    String voteEncourage(Long id, User user);
    String closeScheduleVote(Long id, User user);

}
