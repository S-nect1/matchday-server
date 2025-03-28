package com.example.moim.global.entity;

public enum EventType {
    SOCCER("축구"), FUTSAL("풋살");

    private final String koreanName;

    EventType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static EventType fromKoreanName(String koreanName) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getKoreanName().equals(koreanName)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("해당 한글 이름에 대응하는 EventType(이)가 없습니다: " + koreanName);
    }
}

