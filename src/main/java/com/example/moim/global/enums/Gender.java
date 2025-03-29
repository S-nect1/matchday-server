package com.example.moim.global.enums;

import com.example.moim.global.enums.exception.EnumControllerAdvice;
import com.example.moim.global.exception.ResponseCode;

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
        for (Gender area : Gender.values()) {
            if (area.getKoreanName().equals(koreanName)) {
                return area;
            }
        }
//        throw new IllegalArgumentException("해당 한글 이름에 대응하는 Gender가 없습니다: " + koreanName);
        throw new EnumControllerAdvice(ResponseCode.INVALID_GENDER);
    }
}
