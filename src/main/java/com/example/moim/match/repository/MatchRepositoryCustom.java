package com.example.moim.match.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.match.dto.MatchClubSearchCond;
import com.example.moim.match.dto.MatchSearchCond;
import com.example.moim.match.entity.Match;

import java.util.List;

public interface MatchRepositoryCustom {
    List<Match> findBySearchCond(MatchSearchCond matchSearchCond);
    List<Club> findClubsBySearchCond(MatchClubSearchCond matchClubSearchCond);
}
