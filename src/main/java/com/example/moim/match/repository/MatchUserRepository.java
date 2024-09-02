package com.example.moim.match.repository;

import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchUser;
import com.example.moim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatchUserRepository extends JpaRepository<MatchUser, Long> {

    @Transactional(readOnly = true)
    @Query("select mu from MatchUser mu" +
            " where mu.match = :match" +
            " and mu.user = :user")
    MatchUser findByMatchAndUser(Match match, User user);

    @Transactional(readOnly = true)
    List<MatchUser> findByMatch(Match match);
}
