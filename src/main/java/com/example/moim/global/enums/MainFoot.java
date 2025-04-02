package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum MainFoot {
    RIGHT("오른발"),
    LEFT("왼발"),
    BOTH("양발");

    private final String koreanName;

    MainFoot(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<MainFoot> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
