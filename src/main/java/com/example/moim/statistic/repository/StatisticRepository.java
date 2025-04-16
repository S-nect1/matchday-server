package com.example.moim.statistic.repository;

import com.example.moim.club.entity.Club;
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
    // 현재 시즌 mvp 유저 찾기(이름, 누적 점수)
    @Query("""
            SELECT new com.example.moim.statistic.dto.StatisticDTO$mvpDTO(mu.user.name, SUM(mu.score))
            FROM MatchUser mu
            WHERE mu.club = :club AND mu.season = :season
            GROUP BY mu.user
            ORDER BY SUM(mu.score) DESC
            """)
    List<StatisticDTO.mvpDTO> findTopScorerByClub(@Param("club") Club club, @Param("season") String season);

    // 모임과 시즌으로 전적 찾기
    Optional<Statistic> findByClubAndSeason(Club club, String season);
}
