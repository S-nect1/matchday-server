package com.example.moim.statistic.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.global.enums.SportsType;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.entity.Match;
import com.example.moim.statistic.dto.StatisticDTO;
import com.example.moim.statistic.entity.Statistic;
import com.example.moim.statistic.exception.advice.StatisticControllerAdvice;
import com.example.moim.statistic.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository statisticRepository;
    private final ClubRepository clubRepository;

    // 전적 생성(새로운 시즌)
    // 매년 1월 1일과 7월 1일
    // 스케쥴 말고 배치 처리 고려해보기
    @Scheduled(cron = "0 0 0 1 1,7 ?")
    @Transactional
    public void createNewSeasonStatistics() {
        String currentSeason = Statistic.getCurrentSeason();
        List<Club> clubs = clubRepository.findAll();

        for (Club club : clubs) {
            // 현재 시즌에 해당하는 Statistic이 존재하는지 확인하는 로직(통합, 풋살, 축구)
            Optional<Statistic> existing = statisticRepository.findByClubAndSeasonAndSportsType(club, currentSeason, SportsType.OVERALL);
            if (existing.isEmpty()) {
                Statistic newStatistic = Statistic.createStatistic(club, SportsType.OVERALL);
                Statistic newFulsalStatistic = Statistic.createStatistic(club, SportsType.FUTSAL);
                Statistic newSoccerStatistic = Statistic.createStatistic(club, SportsType.SOCCER);
                statisticRepository.save(newStatistic);
                statisticRepository.save(newFulsalStatistic);
                statisticRepository.save(newSoccerStatistic);
            }
        }
    }

    // 전적 업데이트 로직
    public void updateStatistic(Match match) {
        String currentSeason = Statistic.getCurrentSeason();

        // 통합
        Statistic homeStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL)
                .orElseThrow();
        Statistic awayStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getAwayClub(), currentSeason, SportsType.OVERALL)
                .orElseThrow();
        int homeRankLevel = homeStatistic.getTier().getLevel();
        int awayRankLevel = awayStatistic.getTier().getLevel();

        StatisticDTO.mvpDTO homeMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);
        StatisticDTO.mvpDTO awayMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);

        homeStatistic.updateStatistic(match.getHomeScore(), match.getHomeScore(), homeRankLevel, homeMVPResult.getName(), homeMVPResult.getGoalCount().intValue());
        awayStatistic.updateStatistic(match.getAwayScore(), match.getAwayScore(), awayRankLevel, awayMVPResult.getName(), awayMVPResult.getGoalCount().intValue());

        if (match.getEvent() == SportsType.FUTSAL) {
            // 풋살
            Statistic homeFutsalStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getHomeClub(), currentSeason, SportsType.FUTSAL)
                    .orElseThrow();
            Statistic awayFutsalStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getAwayClub(), currentSeason, SportsType.FUTSAL)
                    .orElseThrow();
            int homeFutsalRankLevel = homeFutsalStatistic.getTier().getLevel();
            int awayFutsalRankLevel = awayFutsalStatistic.getTier().getLevel();

            StatisticDTO.mvpDTO homeFutsalMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);
            StatisticDTO.mvpDTO awayFutsalMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);

            homeFutsalStatistic.updateStatistic(match.getHomeScore(), match.getHomeScore(), homeFutsalRankLevel, homeMVPResult.getName(), homeFutsalMVPResult.getGoalCount().intValue());
            awayFutsalStatistic.updateStatistic(match.getAwayScore(), match.getAwayScore(), awayFutsalRankLevel, awayMVPResult.getName(), awayFutsalMVPResult.getGoalCount().intValue());
        } else if (match.getEvent() == SportsType.SOCCER) {
            // 축구
            Statistic homeSoccerStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getHomeClub(), currentSeason, SportsType.SOCCER)
                    .orElseThrow();
            Statistic awaySoccerStatistic = statisticRepository.findByClubAndSeasonAndSportsType(match.getAwayClub(), currentSeason, SportsType.SOCCER)
                    .orElseThrow();
            int homeSoccerRankLevel = homeSoccerStatistic.getTier().getLevel();
            int awaySoccerRankLevel = awaySoccerStatistic.getTier().getLevel();

            StatisticDTO.mvpDTO homeSoccerMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);
            StatisticDTO.mvpDTO awaySoccerMVPResult = statisticRepository.findTopScorerByClubAndSportsType(match.getHomeClub(), currentSeason, SportsType.OVERALL).get(0);

            homeSoccerStatistic.updateStatistic(match.getHomeScore(), match.getAwayScore(), homeSoccerRankLevel, homeSoccerMVPResult.getName(), homeMVPResult.getGoalCount().intValue());
            awaySoccerStatistic.updateStatistic(match.getAwayScore(), match.getHomeScore(), awaySoccerRankLevel, awaySoccerMVPResult.getName(), awayMVPResult.getGoalCount().intValue());
        }
    }

    // 전적 조회
    public StatisticDTO.StatisticResponse getStatistic(Long clubId, String targetSeason, String sportsType) {
//        String currentSeason = Statistic.getCurrentSeason();
        SportsType targetSportsType = SportsType.valueOf(sportsType);
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        Statistic statistic = statisticRepository.findByClubAndSeasonAndSportsType(club, targetSeason, targetSportsType)
                .orElseThrow(() -> new StatisticControllerAdvice(ResponseCode.STATISTIC_NOT_FOUND));

        return new StatisticDTO.StatisticResponse(statistic);
    }
}
