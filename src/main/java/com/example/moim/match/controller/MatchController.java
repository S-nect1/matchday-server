package com.example.moim.match.controller;

import com.example.moim.club.repository.ClubRepository;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.dto.*;
import com.example.moim.match.exception.advice.MatchControllerAdvice;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.match.service.MatchService;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;
    //매치 등록(생성) 신청(참가)
    //매치 생성
    @PostMapping("/match")
    public BaseResponse<MatchOutput> matchSave(@RequestBody @Valid MatchInput matchInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.saveMatch(userDetailsImpl.getUser(), matchInput), ResponseCode.OK);
    }

    //매치 등록
    @PatchMapping(value = "/match", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<MatchRegOutput> matchRegister(@RequestBody @Valid MatchRegInput matchRegInput,
                                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.registerMatch(userDetailsImpl.getUser(), matchRegInput), ResponseCode.OK);
    }

    //매치 신청 생성
    @PostMapping("/match-apply")
    public BaseResponse<MatchApplyOutput> matchApplySave(@RequestParam Long matchId,
                                           @RequestParam Long clubId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.saveMatchApp(userDetailsImpl.getUser(), matchId, clubId), ResponseCode.OK);
    }

    //매치 신청 완료
    @PatchMapping("/match-apply")
    public BaseResponse<MatchApplyOutput> matchApply(@RequestBody @Valid MatchApplyInput matchApplyInput,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.applyMatch(userDetailsImpl.getUser(), matchApplyInput), ResponseCode.OK);
    }

    //매치 초청
    @PostMapping("/match-invite")
    public BaseResponse<String> matchInvite(@RequestParam Long matchId,
                                              @RequestParam Long clubId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        matchService.inviteMatch(userDetailsImpl.getUser(), matchId, clubId);
        return BaseResponse.onSuccess("매치 초청 알림이 전송되었습니다.", ResponseCode.OK);
    }

    //매치 확정
    @PostMapping("/match-confirm")
    public BaseResponse<MatchConfirmOutput> matchConfirm(@RequestParam Long matchId,
                                           @RequestParam Long clubId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.confirmMatch(matchId, clubId, userDetailsImpl.getUser()), ResponseCode.OK);
    }

    //등록된 매치 검색
    @GetMapping("/matches")
    public BaseResponse<List<MatchSearchOutput>> findMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                               @ModelAttribute MatchSearchCond matchSearchCond) {
        return BaseResponse.onSuccess(matchService.searchMatch(matchSearchCond), ResponseCode.OK);
    }

    //확정된 매치리스트
    @GetMapping("/{clubId}/matches")
    public BaseResponse<List<ConfirmedMatchOutput>> findConfirmedMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                           @PathVariable Long clubId) {
        return BaseResponse.onSuccess(matchService.findConfirmedMatch(clubRepository.findById(clubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND))),
                ResponseCode.OK);
    }

    //활동 지역 소재 모임 리스트
    @GetMapping("/{clubId}/match-clubs")
    public BaseResponse<List<MatchClubOutput>> findMatchClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                @ModelAttribute MatchClubSearchCond matchClubSearchCond,
                                                @PathVariable Long clubId) {
        return BaseResponse.onSuccess(matchService.searchMatchClubs(matchClubSearchCond, clubRepository.findById(clubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND))),
                ResponseCode.OK);
    }

    //매치 등록/신청 현황
    @GetMapping("/{clubId}/match-status")
    public BaseResponse<List<MatchStatusOutput>> getMatchStatus(@PathVariable Long clubId) {
        return BaseResponse.onSuccess(matchService.findMatchStatus(clubRepository.findById(clubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND))),
                ResponseCode.OK);
    }

    //매치 결과 기록
    @PatchMapping(value = "/match/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<MatchRecordOutput> matchRecordSave(@PathVariable Long matchId,
                                             @RequestBody @Valid MatchRecordInput matchRecordInput,
                                             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.saveMatchRecord(matchRepository.findById(matchId)
                        .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_NOT_FOUND)),
                userDetailsImpl.getUser(),
                matchRecordInput),
                ResponseCode.OK);
    }

    //매치 메인 페이지(대시보드)
    @GetMapping("match/main/{clubId}")
    public BaseResponse<MatchMainOutput> findMatchMain(@PathVariable Long clubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(matchService.matchMainFind(clubId, userDetailsImpl.getUser()), ResponseCode.OK);
    }

}
