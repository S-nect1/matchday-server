package com.example.moim.main.controller;

import com.example.moim.main.dto.MainOutput;
import com.example.moim.main.dto.NoClubMainOutput;
import com.example.moim.main.service.MainService;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController implements MainControllerDocs{
    private final MainService mainService;

    @GetMapping("/main")
    public NoClubMainOutput noClubMainPage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return mainService.noClubMainPage(userDetailsImpl.getUser());
    }

    @GetMapping("/main/{clubId}")
    public MainOutput mainPage (@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long clubId) {
        return mainService.mainPage(clubId);
    }
}