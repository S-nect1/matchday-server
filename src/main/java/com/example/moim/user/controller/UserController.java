package com.example.moim.user.controller;

import com.example.moim.user.dto.UserOutput;
import com.example.moim.user.dto.userDetailsImpl;
import com.example.moim.user.dto.JoinInput;
import com.example.moim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @PostMapping("/join")
    public String join(@RequestBody JoinInput joinInput) {
        System.out.println(joinInput.getEmail());
        userService.joinProcess(joinInput);
        return "ok";
    }

    @GetMapping("/user")
    public UserOutput userFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
        return userService.findUser(userDetailsImpl.getUser());
    }

//    @GetMapping("/check")
//    public String check(@AuthenticationPrincipal userDetailsImpl userDetailsImpl) {
//        System.out.println("user = " + userDetailsImpl);
//        System.out.println("user.getId() = " + userDetailsImpl.getUserId());
//        return "ok";
//    }
}
