package com.example.moim.user.controller;

import com.example.moim.user.dto.*;
import com.example.moim.user.service.UserService;
import com.example.moim.user.service.SocialLoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDocs{
    private final UserService userService;
    private final SocialLoginService socialLoginService;

    @PostMapping("/user")
    public String signup(@RequestBody @Valid SignupInput signupInput) {
        userService.signup(signupInput);
        return "ok";
    }

    @PostMapping("/user/login")
    public UserOutput login(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid LoginInput loginInput, HttpServletResponse response) {
        LoginOutput loginOutput = userService.login(loginInput);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        response.addHeader("Authorization-refresh", "Bearer " + loginOutput.getRefreshToken());
        return new UserOutput(loginOutput);
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserOutput userFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return userService.findUser(userDetailsImpl.getUser());
    }

    @GetMapping(value = "/user/club", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MyClubOutput> userClubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return userService.findUserClub(userDetailsImpl.getUser());
    }

    @GetMapping("/user/google")
    public UserOutput googleLogin(@RequestParam String code, HttpServletResponse response) {
        LoginOutput loginOutput = socialLoginService.googleLogin(code);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        response.addHeader("Authorization-refresh", "Bearer " + loginOutput.getRefreshToken());
        return new UserOutput(loginOutput);
    }

    @GetMapping("/user/kakao")
    public UserOutput kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        LoginOutput loginOutput = socialLoginService.kakaoLogin(code);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        response.addHeader("Authorization-refresh", "Bearer " + loginOutput.getRefreshToken());
        return new UserOutput(loginOutput);
    }

    @GetMapping("/user/naver")
    public UserOutput naverLogin(@RequestParam String code, HttpServletResponse response) {
        LoginOutput loginOutput =  socialLoginService.naverLogin(code);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        response.addHeader("Authorization-refresh", "Bearer " + loginOutput.getRefreshToken());
        return new UserOutput(loginOutput);
    }

    @PostMapping("/user/info")
    public void userInfoSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid SocialSignupInput socialSignupInput) {
        userService.saveUserInfo(userDetailsImpl.getUser(), socialSignupInput);
    }

    @GetMapping("/user/refresh/{refreshToken}")
    public UserOutput userRefresh(@PathVariable String refreshToken, HttpServletResponse response) {
        LoginOutput loginOutput = userService.userRefresh(refreshToken);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        response.addHeader("Authorization-refresh", "Bearer " + loginOutput.getRefreshToken());
        return new UserOutput(loginOutput);
    }
}
