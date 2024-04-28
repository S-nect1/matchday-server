package com.example.moim.user.controller;

import com.example.moim.exception.ErrorResult;
import com.example.moim.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "회원 api")
public interface UserControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "409", description = "중복회원가입시 409 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    String signup(@RequestBody SignupInput signupInput);

    @Operation(summary = "로그인", description = "로그인 성공시 응답 헤더 Authorization 에 jwt 발급")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema))
    @ApiResponse(responseCode = "404", description = "로그인 정보 틀리면 404 에러코드와 메세지 응답", content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    void login(@RequestBody LoginInput loginInput, HttpServletResponse response);

    @Operation(summary = "마이페이지 회원 정보 조회")
    UserOutput userFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);

    @Operation(summary = "내가 속한 모임 조회")
    List<MyClubOutput> userClubFind(@AuthenticationPrincipal userDetailsImpl userDetailsImpl);
}
