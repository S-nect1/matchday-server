package com.example.moim.match.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.repository.ClubRepositoryCustom;
import com.example.moim.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

    @Transactional(readOnly = true)
    @Query("select m from Match m" +
            " where m.homeClub = :club")
    List<Match> findMatchByClub(@Param("club") Club club);
}
