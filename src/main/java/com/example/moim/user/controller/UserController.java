package com.example.moim.user.controller;

import com.example.moim.user.dto.*;
import com.example.moim.user.service.UserService;
import com.example.moim.user.service.GoogleUserService;
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
    private final GoogleUserService googleUserService;

    @PostMapping("/user")
    public String signup(@RequestBody @Valid SignupInput signupInput) {
        userService.signup(signupInput);
        return "ok";
    }

    @PostMapping("/user/login")
    public UserOutput login(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid LoginInput loginInput, HttpServletResponse response) {
        LoginOutput loginOutput = userService.login(loginInput);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
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

    @GetMapping("user/google")
    public UserOutput googleLogin(@RequestParam String code, HttpServletResponse response) {
        LoginOutput loginOutput = googleUserService.googleLogin(code);
        response.addHeader("Authorization", "Bearer " + loginOutput.getAccessToken());
        return new UserOutput(loginOutput);
    }

}
