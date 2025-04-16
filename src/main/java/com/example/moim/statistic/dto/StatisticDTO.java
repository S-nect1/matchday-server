package com.example.moim.statistic.dto;

import com.example.moim.statistic.entity.Statistic;
import lombok.AllArgsConstructor;
import lombok.Data;

public class StatisticDTO {
    @Data
    @AllArgsConstructor
    public static class StatisticRequest {
        private String targetSeason;
    }

    @Data
    @AllArgsConstructor
    public static class StatisticResponse {
        private String season;
        private String rank;
        private int point;
        private float winRate;
        private int winCount;
        private int defeatCount;
        private int drawCount;
        private String mvpName;
        private int mvpGoalCount;

        public StatisticResponse(Statistic statistic) {
            this.season = statistic.getSeason();
            this.point = statistic.getPoint();
            this.winRate = statistic.getWinRate();
            this.winCount = statistic.getWinCount();
            this.defeatCount = statistic.getDefeatCount();
            this.drawCount = statistic.getDrawCount();
            this.mvpName = statistic.getMvpName();
            this.mvpGoalCount = statistic.getMvpScore();
            this.rank = statistic.getTier().toString();
        }
    }

    @Data
    @AllArgsConstructor
    public static class mvpDTO {
        private String name;
        private Long goalCount;
    }
}
