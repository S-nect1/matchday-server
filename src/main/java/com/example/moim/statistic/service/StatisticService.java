package com.example.moim.statistic.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
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
            // 현재 시즌에 해당하는 Statistic이 존재하는지 확인하는 로직
            Optional<Statistic> existing = statisticRepository.findByClubAndSeason(club, currentSeason);
            if (existing.isEmpty()) {
                Statistic newStatistic = Statistic.createStatistic(club);
                statisticRepository.save(newStatistic);
            }
        }
    }

    // 전적 업데이트 로직
    public void updateStatistic(Match match) {
        String currentSeason = Statistic.getCurrentSeason();
        Statistic homeStatistic = statisticRepository.findByClubAndSeason(match.getHomeClub(), currentSeason)
                .orElseThrow();
        Statistic awayStatistic = statisticRepository.findByClubAndSeason(match.getAwayClub(), currentSeason)
                .orElseThrow();
        int homeRankLevel = homeStatistic.getTier().getLevel();
        int awayRankLevel = awayStatistic.getTier().getLevel();

        // 1명만 가져오게 페이징
        StatisticDTO.mvpDTO homeMVPResult = statisticRepository.findTopScorerByClub(match.getHomeClub(), currentSeason).getFirst();
        StatisticDTO.mvpDTO awayMVPResult = statisticRepository.findTopScorerByClub(match.getHomeClub(), currentSeason).getFirst();

        homeStatistic.updateStatistic(match.getHomeScore(), match.getAwayScore(), awayRankLevel, homeMVPResult.getName(), homeMVPResult.getGoalCount().intValue());
        awayStatistic.updateStatistic(match.getAwayScore(), match.getHomeScore(), homeRankLevel, awayMVPResult.getName(), awayMVPResult.getGoalCount().intValue());
    }

    // 전적 조회
    public StatisticDTO.StatisticResponse getStatistic(Long clubId, String targetSeason) {
//        String currentSeason = Statistic.getCurrentSeason();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        Statistic statistic = statisticRepository.findByClubAndSeason(club, targetSeason)
                .orElseThrow(() -> new StatisticControllerAdvice(ResponseCode.STATISTIC_NOT_FOUND));

        return new StatisticDTO.StatisticResponse(statistic);
    }
}
