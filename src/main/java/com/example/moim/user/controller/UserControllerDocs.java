package com.example.moim.user.controller;

import com.example.moim.user.dto.JoinInput;
import com.example.moim.user.dto.MyClubOutput;
import com.example.moim.user.dto.UserOutput;
import com.example.moim.user.dto.userDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "회원 api", description = "로그인 api는 /login, 입력값은 {\"email\":\"string\", \"password\":\"string\"}")
public interface UserControllerDocs {

    @Operation(summary = "회원가입", description = "필요 파라미터 : 이메일, 비밀번호")
    String join(@RequestBody JoinInput joinInput);

    @Operation(summary = "마이페이지 회원 정보 조회")
    UserOutput userFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "내가 속한 모임 조회")
    List<MyClubOutput> userClubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);
}
