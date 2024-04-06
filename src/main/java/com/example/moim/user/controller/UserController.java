package com.example.moim.user.controller;

import com.example.moim.user.dto.JoinDTO;
import com.example.moim.user.dto.LoginInput;
import com.example.moim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        
        System.out.println(joinDTO.getEmail());
        userService.joinProcess(joinDTO);
        
        return "ok";
    }
//
//    @PostMapping("/login")
//    public LoginInput login(@RequestBody LoginInput loginInput) {
//        return userService.login(loginInput);
//    }
}
