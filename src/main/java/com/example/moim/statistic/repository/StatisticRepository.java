package com.example.moim.statistic.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.global.enums.SportsType;
import com.example.moim.statistic.dto.StatisticDTO;
import com.example.moim.statistic.entity.Statistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    // 현재 시즌 통합 mvp 유저 찾기(이름, 누적 점수)
//    @Query("""
//            SELECT new com.example.moim.statistic.dto.StatisticDTO$mvpDTO(mu.user.name, SUM(mu.score))
//            FROM MatchUser mu
//            WHERE mu.club = :club AND mu.season = :season
//            GROUP BY mu.user
//            ORDER BY SUM(mu.score) DESC
//            """)
//    List<StatisticDTO.mvpDTO> findTopScorerByClub(@Param("club") Club club, @Param("season") String season);

    // 현재 시즌 종목별 mvp 유저 찾기(이름, 누적 점수)
    @Query("""
            SELECT new com.example.moim.statistic.dto.StatisticDTO$mvpDTO(mu.user.name, SUM(mu.score))
            FROM MatchUser mu
            WHERE mu.club = :club
                AND mu.season = :season
                AND mu.match.event = :sportsType
            GROUP BY mu.user
            ORDER BY SUM(mu.score) DESC
            """)
    List<StatisticDTO.mvpDTO> findTopScorerByClubAndSportsType(@Param("club") Club club,
                                                               @Param("season") String season,
                                                               @Param("sportsType") SportsType sportsType);

    // 모임과 시즌으로 전적 찾기(통합)
    Optional<Statistic> findByClubAndSeason(Club club, String season);

    // 종목별 시즌 전적
    Optional<Statistic> findByClubAndSeasonAndSportsType(Club club, String season, SportsType sportsType);
}
