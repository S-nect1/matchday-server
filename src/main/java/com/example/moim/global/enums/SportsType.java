package com.example.moim.global.enums;

public enum SportsType {
    SOCCER("축구"), FUTSAL("풋살");
    private final String koreanName;

    SportsType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static SportsType fromKoreanName(String koreanName) {
        for (SportsType area : SportsType.values()) {
            if (area.getKoreanName().equals(koreanName)) {
                return area;
            }
        }
        throw new IllegalArgumentException("해당 한글 이름에 대응하는 SportsType가 없습니다: " + koreanName);
    }
}
