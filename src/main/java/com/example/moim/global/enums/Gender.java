package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {
    MAN("남성"), WOMAN("여성"), UNISEX("혼성");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<Gender> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
