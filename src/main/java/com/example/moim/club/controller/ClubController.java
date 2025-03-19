package com.example.moim.club.controller;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSearchOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.club.service.ClubService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubController implements ClubControllerDocs{
    private final ClubService clubService;

    @PostMapping(value = "/club", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ClubOutput> clubSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubInput clubInput) throws IOException {
        return BaseResponse.onSuccess(clubService.saveClub(userDetailsImpl.getUser(), clubInput), ResponseCode.OK);
    }

    @PatchMapping(value = "/club", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ClubOutput> clubUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput) throws IOException {
        return BaseResponse.onSuccess(clubService.updateClub(userDetailsImpl.getUser(), clubUpdateInput), ResponseCode.OK);
    }

    @GetMapping("/clubs")
    public BaseResponse<List<ClubSearchOutput>> findClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubSearchCond clubSearchCond) {
        return BaseResponse.onSuccess(clubService.searchClub(clubSearchCond), ResponseCode.OK);
    }

    @PostMapping("/club/users")
    public BaseResponse<UserClubOutput> clubUserSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubUserSaveInput) {
        return BaseResponse.onSuccess(clubService.saveClubUser(userDetailsImpl.getUser(), clubUserSaveInput), ResponseCode.OK);
    }

//    @PostMapping("/club/users/invite")
//    public UserClubOutput clubUserInvite(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubInviteInput clubInviteInput) {
//        return clubService.inviteClubUser(userDetailsImpl.getUser(), clubInviteInput);
//    }

    @PatchMapping("/club/users")
    public BaseResponse<UserClubOutput> clubUserUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput) {
        return BaseResponse.onSuccess(clubService.updateClubUser(userDetailsImpl.getUser(), clubInput), ResponseCode.OK);
    }

    @GetMapping("/club/{id}")
    public BaseResponse<ClubOutput> clubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
        return BaseResponse.onSuccess(clubService.findClub(id, userDetailsImpl.getUser()), ResponseCode.OK);
    }

    @PatchMapping(value = "/club/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse clubPasswordUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid ClubPswdUpdateInput clubPswdUpdateInput) {
        clubService.clubPasswordUpdate(userDetailsImpl.getUser(), clubPswdUpdateInput);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

//    @PatchMapping(value = "/club/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void profileImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubImgInput clubImgInput) throws IOException {
//        clubService.updateProfileImg(clubImgInput);
//    }
}
