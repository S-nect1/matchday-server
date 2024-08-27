package com.example.moim.match.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.match.entity.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

    @Transactional(readOnly = true)
    @Query("select m from Match m" +
            " where m.homeClub = :club")
    List<Match> findMatchByClub(@Param("club") Club club);

    @Transactional(readOnly = true)
    @Query("select m.account from Match m" +
            " where m.homeClub.id = :clubId" +
            " ORDER BY m.id DESC")
    List<String> findAccountByClubId(@Param("clubId") Long clubId);

    @Transactional(readOnly = true)
    @Query("select m.fee from Match m" +
            " where m.homeClub.id = :clubId" +
            " and m.location = :location" +
            " order by m.id desc")
    List<Integer> findFeeByClubIdAndLocation(@Param("clubId") Long clubId, @Param("location") String location);
}
