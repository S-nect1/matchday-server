package com.example.moim.schedule.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.service.ScheduleCommandService;
import com.example.moim.schedule.service.ScheduleQueryService;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs {
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    private final UserRepository userRepository;

    @PostMapping(value = "/schedules")
//    public BaseResponse<ScheduleOutput> createSchedule(@RequestBody @Valid ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<ScheduleOutput> createSchedule(@RequestBody @Valid ScheduleInput scheduleInput) {
        User user = userRepository.findById(1L).get();
        ScheduleOutput scheduleOutput = scheduleCommandService.saveSchedule(scheduleInput, user);
        return BaseResponse.onSuccess(scheduleOutput, ResponseCode.OK);
    }

    @PatchMapping("/schedules/{id}")
//    public BaseResponse<ScheduleOutput> updateSchedule(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<ScheduleOutput> updateSchedule(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable("id") Long id) {
        User user = userRepository.findById(1L).get();
        ScheduleOutput scheduleOutput = scheduleCommandService.updateSchedule(scheduleUpdateInput, id, user);
        return BaseResponse.onSuccess(scheduleOutput, ResponseCode.OK);
    }

    @GetMapping(value = "/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<List<ScheduleOutput>> searchScheduleListByMonth(@ModelAttribute ScheduleSearchMonthInput scheduleSearchMonthInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<List<ScheduleOutput>> searchScheduleListByMonth(@ModelAttribute ScheduleSearchMonthInput scheduleSearchMonthInput) {
        User user = userRepository.findById(1L).get();
        List<ScheduleOutput> scheduleList = scheduleQueryService.findMonthlySchedulesWithFilter(scheduleSearchMonthInput, user);
        return BaseResponse.onSuccess(scheduleList, ResponseCode.OK);
    }

    @GetMapping(value = "/schedules/day", produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<List<ScheduleOutput>> searchScheduleListByDay(@ModelAttribute ScheduleSearchMonthInput scheduleSearchMonthInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<List<ScheduleOutput>> searchScheduleListByDay(@ModelAttribute ScheduleSearchMonthInput scheduleSearchMonthInput) {
        User user = userRepository.findById(1L).get();
        List<ScheduleOutput> scheduleList = scheduleQueryService.findScheduleByDay(scheduleSearchMonthInput, user);
        return BaseResponse.onSuccess(scheduleList, ResponseCode.OK);
    }

    @GetMapping("/schedules/{id}")
//    public BaseResponse<ScheduleDetailOutput> getScheduleDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<ScheduleDetailOutput> getScheduleDetail(@PathVariable Long id) {
        User user = userRepository.findById(1L).get();
        ScheduleDetailOutput scheduleDetail = scheduleQueryService.findScheduleDetail(id, user);
        return BaseResponse.onSuccess(scheduleDetail, ResponseCode.OK);
    }

    @DeleteMapping("/schedules/{id}")
//    public BaseResponse<String> deleteSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    public BaseResponse<String> deleteSchedule(@PathVariable Long id) {
        User user = userRepository.findById(1L).get();
        String result = scheduleCommandService.deleteSchedule(id, user);
        return BaseResponse.onSuccess(result, ResponseCode.OK);
    }
}
