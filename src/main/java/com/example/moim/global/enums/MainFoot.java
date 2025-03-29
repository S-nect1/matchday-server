package com.example.moim.global.enums;

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

    public static MainFoot fromKoreanName(String koreanName) {
        for (MainFoot foot: MainFoot.values()) {
            if (foot.getKoreanName().equals(koreanName)) {
                return foot;
            }
        }
        throw new IllegalArgumentException("해당 한글 이름에 대응하는 MainFoot(이)가 없습니다: " + koreanName);
    }
}
