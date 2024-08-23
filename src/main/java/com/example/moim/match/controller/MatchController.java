package com.example.moim.match.controller;

import com.example.moim.club.repository.ClubRepository;
import com.example.moim.match.dto.MatchInput;
import com.example.moim.match.dto.MatchOutput;
import com.example.moim.match.dto.MatchSearchCond;
import com.example.moim.match.dto.MatchSearchOutput;
import com.example.moim.match.service.MatchService;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return matchService.createMatch(userDetailsImpl.getUser(), matchInput);
    }

    @GetMapping("/matches")
    public List<MatchSearchOutput> findMatches(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                              @ModelAttribute MatchSearchCond matchSearchCond) {
        return matchService.searchMatch(matchSearchCond);
    }
}
