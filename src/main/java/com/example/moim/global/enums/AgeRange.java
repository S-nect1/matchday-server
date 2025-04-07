package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AgeRange {
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES_PLUS("50대 +");

    private final String koreanName;

    AgeRange(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<AgeRange> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
