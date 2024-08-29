package com.example.moim.match.controller;

import com.example.moim.club.repository.ClubRepository;
import com.example.moim.match.dto.*;
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

    //매치 생성
    @PostMapping("/match")
    public MatchOutput matchSave(@RequestBody @Valid MatchInput matchInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.saveMatch(userDetailsImpl.getUser(), matchInput);
    }

    //매치 등록
    @PatchMapping(value = "/match", produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchRegOutput matchRegister(@RequestBody @Valid MatchRegInput matchRegInput,
                                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.registerMatch(userDetailsImpl.getUser(), matchRegInput);
    }

    //매치 신청 생성
    @PostMapping("/match-apply")
    public MatchApplyOutput matchApplySave(@RequestParam Long matchId,
                                           @RequestParam Long clubId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.saveMatchApp(userDetailsImpl.getUser(), matchId, clubId);
    }

    //매치 신청 완료
    @PatchMapping("/match-apply")
    public MatchApplyOutput matchApply(@RequestBody @Valid MatchApplyInput matchApplyInput,
                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.applyMatch(userDetailsImpl.getUser(), matchApplyInput);
    }

    //매치 초청
    @PostMapping("/match-invite")
    public ResponseEntity<String> matchInvite(@RequestParam Long matchId,
                                              @RequestParam Long clubId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        matchService.inviteMatch(userDetailsImpl.getUser(), matchId, clubId);
        return ResponseEntity.ok("매치 초청 알림이 전송되었습니다.");
    }

    //매치 확정
    @PostMapping("/match-confirm")
    public MatchConfirmOutput matchConfirm(@RequestParam Long matchId,
                                           @RequestParam Long clubId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.confirmMatch(matchId, clubId, userDetailsImpl.getUser());
    }

    //등록된 매치 검색
    @GetMapping("/matches")
    public List<MatchSearchOutput> findMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                               @ModelAttribute MatchSearchCond matchSearchCond) {
        return matchService.searchMatch(matchSearchCond);
    }

    //확정된 매치리스트
    @GetMapping("/{clubId}/matches")
    public List<ConfirmedMatchOutput> findConfirmedMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                           @PathVariable Long clubId) {
        return matchService.findConfirmedMatch(clubRepository.findById(clubId).get());
    }

    //활동 지역 소재 모임 리스트
    @GetMapping("/{clubId}/match-clubs")
    public List<MatchClubOutput> findMatchClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                @ModelAttribute MatchClubSearchCond matchClubSearchCond,
                                                @PathVariable Long clubId) {
        return matchService.searchMatchClubs(matchClubSearchCond, clubRepository.findById(clubId).get());
    }

    //매치 등록/신청 현황
    @GetMapping("/{clubId}/match-status")
    public List<MatchStatusOutput> getMatchStatus(@PathVariable Long clubId) {
        return matchService.findMatchStatus(clubRepository.findById(clubId).get());
    }
}
