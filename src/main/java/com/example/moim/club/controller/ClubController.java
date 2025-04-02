package com.example.moim.club.controller;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSearchOutput;
import com.example.moim.club.dto.response.ClubUpdateOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.club.service.ClubCommandService;
import com.example.moim.club.service.ClubCommandServiceImpl;
import com.example.moim.club.service.ClubQueryService;
import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final ClubCommandService clubCommandService;
    private final ClubQueryService clubQueryService;

    private final UserRepository userRepository;

    @PostMapping(value = "/club", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<ClubOutput> clubSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubInput clubInput) throws IOException {
    public BaseResponse<ClubOutput> clubSave(@ModelAttribute @Valid ClubInput clubInput) throws IOException {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.saveClub(user, clubInput), ResponseCode.OK);
    }

    @PatchMapping(value = "/club/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<ClubOutput> clubUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput) throws IOException {
    public BaseResponse<ClubUpdateOutput> clubUpdate(@ModelAttribute ClubUpdateInput clubUpdateInput, @PathVariable("id") Long clubId) throws IOException {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.updateClub(user, clubUpdateInput, clubId), ResponseCode.OK);
    }

    @GetMapping("/clubs")
//    public BaseResponse<List<ClubSearchOutput>> findClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubSearchCond clubSearchCond) {
    public BaseResponse<List<ClubSearchOutput>> findClubs(@ModelAttribute ClubSearchCond clubSearchCond) {
        return BaseResponse.onSuccess(clubQueryService.searchClub(clubSearchCond), ResponseCode.OK);
    }

    @PostMapping("/club/users")
//    public BaseResponse<UserClubOutput> clubUserSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubUserSaveInput) {
    public BaseResponse<UserClubOutput> clubUserSave(@RequestBody ClubUserSaveInput clubUserSaveInput) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.saveClubUser(user, clubUserSaveInput), ResponseCode.OK);
    }

//    @PostMapping("/club/users/invite")
//    public UserClubOutput clubUserInvite(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubInviteInput clubInviteInput) {
//        return clubService.inviteClubUser(userDetailsImpl.getUser(), clubInviteInput);
//    }

    @PatchMapping("/club/users")
//    public BaseResponse<UserClubOutput> clubUserUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput) {
    public BaseResponse<UserClubOutput> clubUserUpdate(@RequestBody ClubUserUpdateInput clubInput) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.updateClubUser(user, clubInput), ResponseCode.OK);
    }

    @GetMapping("/club/{id}")
//    public BaseResponse<ClubOutput> clubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable Long id) {
    public BaseResponse<ClubOutput> clubFind(@Parameter(description = "조회할 모임 ID", required = true, example = "1") @PathVariable("id") Long id) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubQueryService.findClub(id, user), ResponseCode.OK);
    }

    @PatchMapping(value = "/club/password", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse clubPasswordUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid ClubPswdUpdateInput clubPswdUpdateInput) {
    public BaseResponse clubPasswordUpdate(@RequestBody @Valid ClubPswdUpdateInput clubPswdUpdateInput) {
        User user = userRepository.findById(1L).get();
        clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

//    @PatchMapping(value = "/club/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void profileImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubImgInput clubImgInput) throws IOException {
//        clubService.updateProfileImg(clubImgInput);
//    }
}
