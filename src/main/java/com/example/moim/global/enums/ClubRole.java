package com.example.moim.global.enums;


import java.util.Arrays;
import java.util.Optional;

public enum ClubRole {
    STAFF("운영진"), // 운영진
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
