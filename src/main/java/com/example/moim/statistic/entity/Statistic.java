package com.example.moim.statistic.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static com.example.moim.statistic.entity.Tier.ROOKIE;

@Entity
@Getter
public class Statistic extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private String season;      // -년 전반기 / 후반기
    private Tier tier;
    private int point;

    private float winRate;      // 소수점 한 자리 수까지 표기
    private int winCount;
    private int drawCount;
    private int defeatCount;

    private int winStreak;
    private int defeatStreak;

    private int mvpScore;
    private String mvpName;

    public static String getCurrentSeason() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        String half = (month <= 6) ? "상반기" : "하반기";

        return year + " " + half + "전적";
    }

    // 모임 만들때 기본적으로 하나 생성, 반기별로 하나씩 생성
    public static Statistic createStatistic(Club club) {
        Statistic statistic = new Statistic();

        statistic.club = club;
        statistic.season = Statistic.getCurrentSeason();
        statistic.tier = ROOKIE;
        statistic.point = 305;
        statistic.winRate = 0f;
        statistic.winCount = 0;
        statistic.drawCount = 0;
        statistic.defeatCount = 0;
        statistic.winStreak = 0;
        statistic.defeatStreak = 0;
        statistic.mvpScore = 0;
        statistic.mvpName = null;

        return statistic;
    }

    public void updateStatistic(int homeScore, int awayScore, int opponentRank, String mvpName, int mvpScore) {
        int currentMatches = this.winCount + this.drawCount + this.defeatCount; // 배치고사 단계: 3경기 미만, 이후 경기부터는 정식 랭크 적용
        int pointsChange = 0;

        int ourRankLevel = this.tier.getLevel();
        int diff = opponentRank - ourRankLevel;

        if (homeScore > awayScore) {
            this.winCount++;
            this.winStreak += this.winStreak + 1;
            this.defeatStreak = 0;

            if (this.tier == ROOKIE) {
                pointsChange += 125;
            } else {
                pointsChange += 128;
                // 3연승 이상이면 연승 보너스: 현재 연승 수 × 10점을 추가
                if (this.winStreak >= 3) {
                    pointsChange += this.winStreak * 10;
                }
                // 랭크 차이 보너스: 우리 팀의 랭크와 상대팀의 랭크 차이에 따라 추가 점수를 부여
                if (diff == 2) {
                    pointsChange += 100;
                } else if (diff >= 3) {
                    pointsChange += 150;
                }
            }
        } else if (homeScore < awayScore) {
            this.defeatCount++;
            this.defeatStreak += this.defeatStreak + 1;
            this.winStreak = 0;

            if (this.tier == ROOKIE) {
                pointsChange -= 75;
            } else {
                // 기본 패배 페널티는 -72점
                // 단, 연패 완화 로직: 3연패 시에는 -30점, 4연패 이상이면 페널티가 0점
                if (this.defeatStreak == 3) {
                    pointsChange += -30;
                } else if (this.defeatStreak >= 4) {
                    pointsChange += 0;
                } else {
                    pointsChange += -72;
                }
            }
        } else {
            this.drawCount++;
            this.winStreak = 0;
            this.defeatStreak = 0;

            if (this.tier == ROOKIE) {
                pointsChange += 50;
            } else {
                pointsChange += 47;
            }
        }

        this.point += pointsChange;

        boolean isRookie = (currentMatches < 3);
        if (!isRookie) {
            if (this.point >= 1600) {
                this.tier = Tier.M1;
            } else if (this.point >= 1300) {
                this.tier = Tier.M2;
            } else if (this.point >= 875) {
                this.tier = Tier.M3;
            } else if (this.point >= 500) {
                this.tier = Tier.M4;
            } else {
                this.tier = Tier.M5;
            }
        }

        this.mvpName = mvpName;
        this.mvpScore = mvpScore;

        // 총 경기수 계산 후 승률 업데이트 (승률 = (승리수/총경기수)*100)
        int totalMatches = this.winCount + this.drawCount + this.defeatCount;
        this.winRate = totalMatches > 0 ? ((float) this.winCount / totalMatches) * 100 : 0;
    }

    public void updateMvp(UserClub userClub) {
        this.mvpName = userClub.getUser().getName();
        this.mvpScore = userClub.getScore();
    }
}
