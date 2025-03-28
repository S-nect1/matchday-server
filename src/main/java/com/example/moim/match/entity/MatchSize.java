package com.example.moim.match.entity;

public enum MatchSize {
    SEVEN("7명"), ELEVEN("11명");

    private final String koreanName;

    MatchSize(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static MatchSize fromKoreanName(String koreanName) {
        for (MatchSize size : MatchSize.values()) {
            if (size.getKoreanName().equals(koreanName)) {
                return size;
            }
        }
        throw new IllegalArgumentException("해당 한글 이름에 대응하는 MatchSize가 없습니다" + koreanName);
    }
}
