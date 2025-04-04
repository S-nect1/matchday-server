package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ClubCategory {
    SMALL_GROUP("소모임"),
    SCHOOL_GROUP("동아리"),
    SOCIAL_GROUP("동호회");

    private final String koreanName;

    ClubCategory(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<ClubCategory> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
