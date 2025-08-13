package com.example.moim.match.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.match.dto.*;
import com.example.moim.user.dto.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "매치 api", description = "매치 관련 api")
public interface MatchControllerDocs {
    @Operation(summary = "매치 생성")
    BaseResponse<MatchOutput> matchSave(@RequestBody @Valid MatchInput matchInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 등록")
    BaseResponse<MatchRegOutput> matchRegister(@RequestBody @Valid MatchRegInput matchRegInput,
                                               @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 생성 취소")
    BaseResponse<String> matchCreationCancel(@RequestParam Long matchId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 신청 생성")
    BaseResponse<MatchApplyOutput> matchApplySave(@RequestParam Long matchId,
                                                  @RequestParam Long clubId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 신청 취소")
    BaseResponse<String> matchApplyCancel(@RequestParam Long matchId,
                                          @RequestParam Long clubId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 신청 완료")
    BaseResponse<MatchApplyOutput> matchApply(@RequestBody @Valid MatchApplyInput matchApplyInput,
                                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);


    @Operation(summary = "매치 초청")
    BaseResponse<String> matchInvite(@RequestParam Long matchId,
                                     @RequestParam Long clubId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 확정")
    BaseResponse<MatchConfirmOutput> matchConfirm(@RequestParam Long matchId,
                                                  @RequestParam Long clubId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 확정 취소")
    BaseResponse<String> cancelConfirmedMatch(@RequestParam Long matchId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "등록된 매치 검색")
    BaseResponse<List<MatchSearchOutput>> findMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                      @ModelAttribute MatchSearchCond matchSearchCond);

    @Operation(summary = "확정된(매치성사된) 매치 리스트")
    BaseResponse<List<ConfirmedMatchOutput>> findConfirmedMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                                  @PathVariable Long clubId);

    @Operation(summary = "활동 지역 소재 모임 리스트")
    BaseResponse<List<MatchClubOutput>> findMatchClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                       @ModelAttribute MatchClubSearchCond matchClubSearchCond,
                                                       @PathVariable Long clubId);

    @Operation(summary = "매치 등록/신청 현황")
    BaseResponse<List<MatchStatusOutput>> getMatchStatus(@PathVariable Long clubId);


    @Operation(summary = "매치 결과 기록")
    BaseResponse<MatchRecordOutput> matchRecordSave(@PathVariable Long matchId,
                                                    @RequestBody @Valid MatchRecordInput matchRecordInput,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "매치 메인 페이지(대시보드)")
    BaseResponse<MatchMainOutput> findMatchMain(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl);
}
