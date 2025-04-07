package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum SportsType {
    SOCCER("축구"), FUTSAL("풋살");
    private final String koreanName;

    SportsType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<SportsType> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
