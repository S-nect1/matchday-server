package com.example.moim.global.enums;

import com.example.moim.global.enums.exception.EnumControllerAdvice;
import com.example.moim.global.exception.ResponseCode;

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

    public static ClubCategory fromKoreanName(String koreanName) {
        for (ClubCategory area : ClubCategory.values()) {
            if (area.getKoreanName().equals(koreanName)) {
                return area;
            }
        }
//        throw new IllegalArgumentException("해당 한글 이름에 대응하는 ClubCategory가 없습니다: " + koreanName);
        throw new EnumControllerAdvice(ResponseCode.INVALID_CLUB_CATEGORY);
    }
}
