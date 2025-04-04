package com.example.moim.global.enums;


import java.util.Arrays;
import java.util.Optional;

public enum ClubRole {
    PRESIDENT("회장"),     // 회장
    VICE_PRESIDENT("부회장"), // 부회장
    GENERAL_AFFAIRS("총무"), // 총무
    MEMBER("일반회원"); // 일반회원

    private final String koreanName;

    ClubRole(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<ClubRole> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
