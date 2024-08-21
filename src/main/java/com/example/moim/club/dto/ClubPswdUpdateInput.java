package com.example.moim.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClubPswdUpdateInput {
    private Long id;
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String oldPassword;
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String rePassword;
}
