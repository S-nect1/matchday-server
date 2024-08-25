package com.example.moim.match.controller;

import com.example.moim.club.repository.ClubRepository;
import com.example.moim.match.dto.*;
import com.example.moim.match.service.MatchService;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final ClubRepository clubRepository;

    @PostMapping("/match")
    public MatchOutput matchSave(@RequestBody @Valid MatchInput matchInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.saveMatch(userDetailsImpl.getUser(), matchInput);
    }

    @PatchMapping(value = "/match", produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchRegOutput matchReg(@RequestBody @Valid MatchRegInput matchRegInput, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return matchService.registerMatch(userDetailsImpl.getUser(), matchRegInput);
    }

    @GetMapping("/match")
    public List<MatchSearchOutput> findMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                               @ModelAttribute MatchSearchCond matchSearchCond) {
        return matchService.searchMatch(matchSearchCond);
    }

}
