package com.example.moim.club.controller;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.*;
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

    @PostMapping(value = "/clubs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<ClubSaveOutput> clubSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubInput clubInput) throws IOException {
    public BaseResponse<ClubSaveOutput> clubSave(@ModelAttribute @Valid ClubInput clubInput) throws IOException {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.saveClub(user, clubInput), ResponseCode.OK);
    }

    @PatchMapping(value = "/clubs/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<ClubUpdateOutput> clubUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubUpdateInput clubUpdateInput, @PathVariable("id") Long clubId) throws IOException {
    public BaseResponse<ClubUpdateOutput> clubUpdate(@ModelAttribute ClubUpdateInput clubUpdateInput, @PathVariable("id") Long clubId) throws IOException {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.updateClub(user, clubUpdateInput, clubId), ResponseCode.OK);
    }

    @GetMapping("/clubs")
//    public BaseResponse<List<ClubSearchOutput>> findClubs(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ClubSearchCond clubSearchCond) {
    public BaseResponse<List<ClubSearchOutput>> findClubs(@ModelAttribute ClubSearchCond clubSearchCond) {
        return BaseResponse.onSuccess(clubQueryService.searchClub(clubSearchCond), ResponseCode.OK);
    }

    @PostMapping("/clubs/{id}/join")
//    public BaseResponse<UserClubOutput> clubUserSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserSaveInput clubUserSaveInput, @PathVariable("id") Long clubId) {
    public BaseResponse<UserClubOutput> clubUserSave(@RequestBody ClubUserSaveInput clubUserSaveInput, @PathVariable("id") Long clubId) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.saveClubUser(user, clubUserSaveInput, clubId), ResponseCode.OK);
    }

//    @PostMapping("/club/users/invite")
//    public UserClubOutput clubUserInvite(@AuthenticationPrincipal userDetailsImpl userDetailsImpl, @RequestBody ClubInviteInput clubInviteInput) {
//        return clubService.inviteClubUser(userDetailsImpl.getUser(), clubInviteInput);
//    }

    @DeleteMapping("/clubs/{id}/users")
//    public BaseResponse<String> clubUserDelete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable("id") Long clubId, @RequestBody ClubUserDeleteInput clubUserDeleteInput) {
    public BaseResponse<String> clubUserDelete(@PathVariable("id") Long clubId, @RequestBody ClubUserDeleteInput clubUserDeleteInput) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.deleteClubUser(user, clubId, clubUserDeleteInput.getUserId()), ResponseCode.OK);
    }

    @PatchMapping("/clubs/{id}/role")
//    public BaseResponse<UserClubOutput> clubUserUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ClubUserUpdateInput clubInput, @PathVariable("id") Long clubId) {
    public BaseResponse<UserClubOutput> clubUserUpdate(@RequestBody ClubUserUpdateInput clubInput, @PathVariable("id") Long clubId) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.updateClubUser(user, clubInput, clubId), ResponseCode.OK);
    }

    @GetMapping("/clubs/{id}")
//    public BaseResponse<ClubOutput> clubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable("id") Long clubId) {
    public BaseResponse<ClubOutput> clubFind(@PathVariable("id") Long clubId) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubQueryService.findClub(clubId, user), ResponseCode.OK);
    }

    @PatchMapping(value = "/clubs/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<String> clubPasswordUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid ClubPswdUpdateInput clubPswdUpdateInput, @PathVariable("id") Long clubId) {
    public BaseResponse clubPasswordUpdate(@RequestBody @Valid ClubPswdUpdateInput clubPswdUpdateInput, @PathVariable("id") Long clubId) {
        User user = userRepository.findById(1L).get();
        return BaseResponse.onSuccess(clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput, clubId), ResponseCode.OK);
    }

//    @PatchMapping(value = "/club/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void profileImgUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Valid ClubImgInput clubImgInput) throws IOException {
//        clubService.updateProfileImg(clubImgInput);
//    }
}
