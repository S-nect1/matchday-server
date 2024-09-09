package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
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
    ScheduleOutput scheduleSave(@RequestBody ScheduleInput scheduleInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 수정", description = "startTime, endTime 형식은 yyyy-MM-dd HH:mm")
    ScheduleOutput scheduleUpdate(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "한달 일정 조회", description = "쿼리파라미터 예시: /schedule?date=202404&clubId=6&search=친선 경기&category=친선 경기")
    List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput);

    @Operation(summary = "하루 일정 조회", description = "쿼리파라미터 예시: /schedule?date=20240910&clubId=6&search=친선 경기&category=친선 경기")
    List<ScheduleOutput> dayScheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput);

    @Operation(summary = "일정 세부 조회", description = "참가면 attendance = attend, 참가 취소는 absent, 투표 안하면 notVote")
    ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id);

    @Operation(summary = "일정 참가 투표", description = "참가면 attendance = attend, 참가 취소는 absent")
    void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 댓글", description = "id는 일정 id")
    void scheduleComment(@RequestBody CommentInput commentInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
}
