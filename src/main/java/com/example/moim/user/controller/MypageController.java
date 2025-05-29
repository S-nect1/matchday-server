package com.example.moim.user.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
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
    public BaseResponse<Void> userInfoUpdate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute UserUpdateInput userUpdateInput) throws IOException {
        userService.updateUserInfo(userDetailsImpl.getUser(), userUpdateInput);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

    @GetMapping("/user/club/mypage")
    public BaseResponse<List<MypageClubOutput>> findMypageClub(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return BaseResponse.onSuccess(userService.findMypageClub(userDetailsImpl.getUser()), ResponseCode.OK);
    }

    @DeleteMapping("/user/club/{userClubId}")
    public BaseResponse<Void> userClubDelete(@PathVariable Long userClubId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        userService.deleteUserClub(userClubId);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }
}
