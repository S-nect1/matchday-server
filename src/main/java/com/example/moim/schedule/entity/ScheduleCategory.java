package com.example.moim.schedule.entity;

import java.util.Arrays;
import java.util.Optional;

public enum ScheduleCategory {
    REGULAR_TRAINING("정기 운동"),
    TOURNAMENT("대회"),
    FRIENDLY_MATCH("친선 매치"),
    ETC("기타");

    private final String koreanName;

    ScheduleCategory(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<ScheduleCategory> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
