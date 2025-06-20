package com.example.moim.schedule.vote.entity;

import java.util.Arrays;
import java.util.Optional;

public enum AttendanceType {
    ATTEND("참석", true), ABSENT("불참", false);
    private final String koreanName;
    private final Boolean isAttendance;

    AttendanceType(String koreanName, boolean isAttendance) {
        this.koreanName = koreanName;
        this.isAttendance = isAttendance;
    }

    public String getKoreanName() {
        return koreanName;
    }
    public boolean getIsAttendance() {
        return isAttendance;
    }

    public static Optional<AttendanceType> fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(g -> g.getKoreanName().equals(koreanName))
                .findFirst();
    }
}
