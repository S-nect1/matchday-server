package com.example.moim.match.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {

    @Transactional(readOnly = true)
    @Query("select ma from MatchApplication ma" +
            " where ma.match = :match")
    List<MatchApplication> findByMatch(@Param("match") Match match);

    @Transactional(readOnly = true)
    @Query("select ma from MatchApplication ma" +
            " where ma.match = :match" +
            " and ma.club = :club")
    MatchApplication findByMatchAndClub(@Param("match") Match match, @Param("club") Club club);
}
