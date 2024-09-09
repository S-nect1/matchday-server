package com.example.moim.user.controller;

import com.example.moim.user.dto.MypageClubOutput;
import com.example.moim.user.dto.UserDetailsImpl;
import com.example.moim.user.dto.UserUpdateInput;
import com.example.moim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController implements MypageControllerDocs {
    private final UserService userService;

    @PatchMapping(value = "/user/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void userInfoUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute UserUpdateInput userUpdateInput) throws IOException {
        userService.updateUserInfo(userDetailsImpl.getUser(), userUpdateInput);
    }

    @GetMapping("/user/club/mypage")
    public List<MypageClubOutput> findMypageClub(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return userService.findMypageClub(userDetailsImpl.getUser());
    }

    @DeleteMapping("/user/club/{userClubId}")
    public void userClubDelete(@PathVariable Long userClubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        userService.deleteUserClub(userClubId);
    }
}
