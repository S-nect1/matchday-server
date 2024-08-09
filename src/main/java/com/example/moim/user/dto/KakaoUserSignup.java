package com.example.moim.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoUserSignup {
    private String email;

    public KakaoUserSignup(String email) {
        this.email = email;
    }
}
