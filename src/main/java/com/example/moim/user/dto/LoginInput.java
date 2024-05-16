package com.example.moim.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginInput {
    @Email(message = "올바른 형식의 이메일 주소여야 합니다.")
    private String email;
    private String password;
    private String fcmToken;
}
