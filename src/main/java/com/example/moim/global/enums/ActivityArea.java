package com.example.moim.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ActivityArea {
    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    BUSAN("부산"),
    ULSAN("울산"),
    DAEGU("대구"),
    GYEONGNAM("경남"),
    GYEONGBUK("경북"),
    DAEJEON("대전"),
    CHUNGNAM("충남"),
    CHUNGBUK("충북"),
    JEONBUK("전북"),
    GWANGJU("광주"),
    JEONNAM("전남"),
    GANGWON("강원"),
    JEJU("제주"),
    SEJONG("세종");

    private final String koreanName;

    ActivityArea(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<ActivityArea> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
