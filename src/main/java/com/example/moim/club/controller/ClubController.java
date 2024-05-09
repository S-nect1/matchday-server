package com.example.moim.club.controller;

import com.example.moim.club.dto.*;
import com.example.moim.club.service.ClubService;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
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
    public ClubOutput clubSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubInput clubInput) throws IOException {
        return clubService.saveClub(userDetailsImpl.getUser(), clubInput);
    }

    @PatchMapping("/club")
    public ClubOutput clubUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput) throws IOException {
        return clubService.updateClub(userDetailsImpl.getUser(), clubUpdateInput);
    }

    @PostMapping("/club/users")
    public UserClubOutput clubUserSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubUserSaveInput) {
        return clubService.saveClubUser(userDetailsImpl.getUser(), clubUserSaveInput);
    }

//    @PostMapping("/club/users/invite")
//    public UserClubOutput clubUserInvite(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubInviteInput clubInviteInput) {
//        return clubService.inviteClubUser(userDetailsImpl.getUser(), clubInviteInput);
//    }

    @PatchMapping("/club/users")
    public UserClubOutput clubUserUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput) {
        return clubService.updateClubUser(userDetailsImpl.getUser(), clubInput);
    }

    @GetMapping("/club/{id}")
    public ClubOutput clubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
        return clubService.findClub(id, userDetailsImpl.getUser());
    }

    @PatchMapping(value = "/club/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void profileImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubImgInput clubImgInput) throws IOException {
        clubService.updateProfileImg(clubImgInput);
    }

    @PatchMapping(value = "/club/background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void backgroundImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubImgInput clubImgInput) throws IOException {
        clubService.updateBackgroundImg(clubImgInput);
    }
}
