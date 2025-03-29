package com.example.moim.global.enums;

import com.example.moim.global.enums.exception.EnumControllerAdvice;
import com.example.moim.global.exception.ResponseCode;

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

    public static AgeRange fromKoreanName(String koreanName) {
        for (AgeRange area : AgeRange.values()) {
            if (area.getKoreanName().equals(koreanName)) {
                return area;
            }
        }
//        throw new IllegalArgumentException("해당 한글 이름에 대응하는 AgeRange가 없습니다: " + koreanName);
        throw new EnumControllerAdvice(ResponseCode.INVALID_AGE_RANGE);
    }
}
