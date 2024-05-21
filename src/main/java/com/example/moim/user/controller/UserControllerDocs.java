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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "회원 api")
public interface UserControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "409", description = "중복회원가입시 409 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    String signup(@RequestBody @Valid SignupInput signupInput);

    @Operation(summary = "로그인", description = "로그인 성공시 응답 헤더 Authorization 에 accessToken, Authorization-refresh 에 refreshToken 발급, 로그인시 fcm 토큰 넣어서 요청")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserOutput.class)))
    @ApiResponse(responseCode = "400", description = "로그인 정보 틀리면 400 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    UserOutput login(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody LoginInput loginInput, HttpServletResponse response);

    @Operation(summary = "마이페이지 회원 정보 조회")
    UserOutput userFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "내가 속한 모임 조회")
    List<MyClubOutput> userClubFind(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl);

    @Operation(summary = "구글 계정으로 회원가입/로그인", description = """
            Authorization 에 accessToken, Authorization-refresh 에 refreshToken 발급

            https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?client_id=988293661923-5h2av5l3ilbqmu653l3i0m8kptap3l5k.apps.googleusercontent.com&redirect_uri=http%3A%2F%2Fec2-43-201-38-151.ap-northeast-2.compute.amazonaws.com%3A18008%2Fuser%2Fgoogle&response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuser.gender.read%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&service=lso&o2v=2&ddm=0&flowName=GeneralOAuthFlow""")

    UserOutput googleLogin(@RequestParam @Schema(description = "google oauth2.0 authorization code") String code, HttpServletResponse response);

    @Operation(summary = "카카오 계정으로 회원가입/로그인", description = """
            Authorization 에 accessToken, Authorization-refresh 에 refreshToken 발급
            
            https://kauth.kakao.com/oauth/authorize?client_id=9d0793ca3b227d6322833657b4678e07&redirect_uri=http://43.201.38.151:18008/user/kakao&response_type=code""")
    UserOutput kakaoLogin(@RequestParam @Schema(description = "kakao oauth2.0 authorization code")String code, HttpServletResponse response);

    @Operation(summary = "네이버 계정으로 회원가입/로그인", description = """
            Authorization 에 accessToken, Authorization-refresh 에 refreshToken 발급
            
            https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=SuAxjstrc2Dn0XMPprY0&redirect_uri=http://43.201.38.151:18008/user/naver&state=%2F%2A-%2B""")
    UserOutput naverLogin(@RequestParam String code, HttpServletResponse response);

    @Operation(summary = "소셜로그인 후 유저 정보 입력")
    void userInfoSave(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody @Valid SocialSignupInput socialSignupInput);

    @Operation(summary = "accessToken 이 만료되었을 때 refreshToken 으로 로그인", description = "Authorization 에 accessToken, Authorization-refresh 에 refreshToken 발급")
    UserOutput userRefresh(@PathVariable String refreshToken, HttpServletResponse response);
}
