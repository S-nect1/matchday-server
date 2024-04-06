package com.example.moim.club.controller;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.dto.ClubOutput;
import com.example.moim.club.service.ClubService;
import com.example.moim.user.dto.userDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping("/club")
    public ClubOutput clubSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubInput clubInput) {
        return clubService.saveClub(userDetailsImpl.getUser(), clubInput);
    }
}
