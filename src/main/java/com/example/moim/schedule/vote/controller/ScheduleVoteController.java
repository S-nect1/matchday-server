package com.example.moim.schedule.vote.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.vote.dto.ScheduleVoteInput;
import com.example.moim.schedule.vote.service.ScheduleVoteService;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleVoteController implements ScheduleVoteControllerDocs {

    private final ScheduleVoteService scheduleVoteService;

    private final UserRepository userRepository;

    @PostMapping("/schedules/{id}/vote")
//    public BaseResponse<String> createScheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<String> createScheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @PathVariable("id") Long id) {

        User user = userRepository.findById(1L).get();
        String result = scheduleVoteService.voteSchedule(scheduleVoteInput, id, user);

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }

    @PostMapping("/schedules/{id}/encouragements")
//    public BaseResponse<String> encourageVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<String> encourageVote(@PathVariable Long id) {

        User user = userRepository.findById(1L).get();
        String result = scheduleVoteService.voteEncourage(id, user);

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }

    @PatchMapping("/schedules/{id}/close-actions")
//    public BaseResponse<String> closeScheduleVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<String> closeScheduleVote(@PathVariable Long id) {

        User user = userRepository.findById(1L).get();
        String result = scheduleVoteService.closeScheduleVote(id, user);

        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }
}
