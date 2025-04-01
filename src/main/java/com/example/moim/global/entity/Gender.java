package com.example.moim.global.entity;

public enum Gender {
    MAN("남성"), WOMAN("여성"), UNISEX("혼성");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Gender fromKoreanName(String koreanName) {
        for (Gender gender : Gender.values()) {
            if (gender.getKoreanName().equals(koreanName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("해당 한글 이름에 대응하는 Gender가 없습니다: " + koreanName);
    }
}
