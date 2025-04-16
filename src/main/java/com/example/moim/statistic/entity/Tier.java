package com.example.moim.statistic.entity;

import lombok.Getter;

@Getter
public enum Tier {
    M1(1),
    M2(2),
    M3(3),
    M4(4),
    M5(5),
    ROOKIE(0);

    private final int level;

    Tier(int level) {
        this.level = level;
    }
}
