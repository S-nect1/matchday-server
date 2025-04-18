package com.example.moim.schedule.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.schedule.dto.ScheduleDetailOutput;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleOutput;
import com.example.moim.schedule.dto.ScheduleSearchInput;
import com.example.moim.schedule.dto.ScheduleUpdateInput;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "일정 api", description = "모임(club) 안에서 category에 따라 권한 부여. creator, admin / member, newmember")
public interface ScheduleControllerDocs {
    @Operation(summary = "일정 생성", description = "startTime, endTime 형식은 yyyy-MM-dd HH:mm")
    BaseResponse<ScheduleOutput> createSchedule(@RequestBody ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 수정", description = "startTime, endTime 형식은 yyyy-MM-dd HH:mm")
    BaseResponse<ScheduleOutput> updateSchedule(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @PathVariable Long scheduleId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "한달 일정 조회", description = "카테고리는 친선 매치/정기 운동/대회/기타 중에 하나여야 합니다(띄어쓰기까지 포함)")
    BaseResponse<List<ScheduleOutput>> searchScheduleList(@ModelAttribute ScheduleSearchInput scheduleSearchInput);

    @Operation(summary = "하루 일정 조회", description = "쿼리파라미터 예시: /schedule/day?date=20240910&clubId=6&search=친선 경기&category=친선 경기")
    BaseResponse<List<ScheduleOutput>> getScheduleListByDay(@ModelAttribute ScheduleSearchInput scheduleSearchInput);

    @Operation(summary = "일정 세부 조회", description = "참가면 attendance = attend, 참가 취소는 absent, 투표 안하면 notVote")
    BaseResponse<ScheduleDetailOutput> getScheduleDetail(@PathVariable Long id);

    @Operation(summary = "일정 삭제")
    BaseResponse<String> deleteSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
//    BaseResponse<String> deleteSchedule(@PathVariable Long id);

}
