package com.example.moim.match.repository;

import com.example.moim.club.repository.ClubRepositoryCustom;
import com.example.moim.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

}
