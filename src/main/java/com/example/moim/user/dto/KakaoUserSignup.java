package com.example.moim.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoUserSignup {
    private String gender;
    private String email;

    public KakaoUserSignup(String gender, String email) {
        this.gender = gender;
        this.email = email;
    }
}
