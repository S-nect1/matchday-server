package com.example.moim.club.controller;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.dto.ClubOutput;
import com.example.moim.club.dto.ClubUserUpdateInput;
import com.example.moim.club.dto.UserClubOutput;
import com.example.moim.club.service.ClubService;
import com.example.moim.user.dto.userDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ClubController implements ClubControllerDocs{
    private final ClubService clubService;

    @PostMapping(value = "/club", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClubOutput clubSave(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @ModelAttribute ClubInput clubInput) throws IOException {
        return clubService.saveClub(userDetailsImpl.getUser(), clubInput);
    }

    @PatchMapping("/club/users")
    public UserClubOutput clubUserUpdate(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput) {
        return clubService.updateClubUser(clubInput);
    }

    @GetMapping("/club/{id}")
    public ClubOutput clubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @PathVariable Long id) {
        return clubService.findClub(id);
    }

}
