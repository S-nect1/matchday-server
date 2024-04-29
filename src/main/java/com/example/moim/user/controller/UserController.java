package com.example.moim.user.controller;

import com.example.moim.user.dto.*;
import com.example.moim.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDocs{
    private final UserService userService;

    @PostMapping("/user")
    public String signup(@RequestBody @Valid SignupInput signupInput) {
        userService.signup(signupInput);
        return "ok";
    }

    @PostMapping("/user/login")
    public void login(@RequestBody @Valid LoginInput loginInput, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + userService.login(loginInput));
    }

    @GetMapping("/user")
    public UserOutput userFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return userService.findUser(userDetailsImpl.getUser());
    }

    @GetMapping("/user/club")
    public List<MyClubOutput> userClubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return userService.findUserClub(userDetailsImpl.getUser());
    }

//    @GetMapping("/check")
//    public String check(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
//        System.out.println("user = " + userDetailsImpl);
//        System.out.println("user.getId() = " + userDetailsImpl.getUserId());
//        return "ok";
//    }
}
