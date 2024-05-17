package com.example.moim.user.controller;

import com.example.moim.exception.ErrorResult;
import com.example.moim.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "회원 api")
public interface UserControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "409", description = "중복회원가입시 409 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    String signup(@RequestBody @Valid SignupInput signupInput);

    @Operation(summary = "로그인", description = "로그인 성공시 응답 헤더 Authorization 에 jwt 발급, 로그인시 fcm 토큰 넣어서 요청")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserOutput.class)))
    @ApiResponse(responseCode = "400", description = "로그인 정보 틀리면 400 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    UserOutput login(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody LoginInput loginInput, HttpServletResponse response);

    @Operation(summary = "마이페이지 회원 정보 조회")
    UserOutput userFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "내가 속한 모임 조회")
    List<MyClubOutput> userClubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "구글 계정으로 회원가입/로그인", description = "로그인 성공시 응답 헤더 Authorization 에 jwt 발급")
    UserOutput googleLogin(@RequestParam @Schema(description = "google oauth2.0 authorization code") String code, HttpServletResponse response);

    @Operation(summary = "카카오 계정으로 회원가입/로그인", description = "로그인 성공시 응답 헤더 Authorization 에 jwt 발급")
    UserOutput kakaoLogin(@RequestParam String code, HttpServletResponse response);

    @Operation(summary = "소셜로그인 후 유저 정보 입력", description = "")
    void userInfoSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid SocialSignupInput socialSignupInput);
}
