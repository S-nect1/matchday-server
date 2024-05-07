package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
import com.example.moim.user.dto.userDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "일정 api")
public interface ScheduleControllerDocs {
    @Operation(summary = "일정 생성", description = "startTime, endTime 형식은 yyyy-MM-dd HH:mm")
    ScheduleOutput scheduleSave(@RequestBody ScheduleInput scheduleInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 수정", description = "startTime, endTime 형식은 yyyy-MM-dd HH:mm")
    ScheduleOutput scheduleUpdate(@RequestBody ScheduleUpdateInput scheduleUpdateInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 조회", description = "쿼리파라미터 예시: /schedule?date=202404&clubId=6")
    List<ScheduleOutput> scheduleFind(@ModelAttribute ScheduleSearchInput scheduleSearchInput);

    @Operation(summary = "일정 세부 조회")
    ScheduleDetailOutput scheduleDetailFind(@PathVariable Long id);

    @Operation(summary = "일정 참가 투표", description = "참가면 attendance = attend, 불참은 absent, 미정은 undecided")
    void scheduleVote(@RequestBody ScheduleVoteInput scheduleVoteInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "일정 댓글", description = "id는 일정 id")
    void scheduleComment(@RequestBody CommentInput commentInput, @AuthenticationPrincipal userDetailsImpl userDetailsImpl);
}
