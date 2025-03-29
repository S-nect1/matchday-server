package com.example.moim.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubPswdUpdateInput {
    private Long id;
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String oldPassword;
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String rePassword;

    @Builder
    public ClubPswdUpdateInput(Long id, String oldPassword, String newPassword, String rePassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.rePassword = rePassword;
    }
}
