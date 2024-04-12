package com.example.moim.club.repository;

import com.example.moim.club.entity.Award;
import com.example.moim.club.entity.Club;

import java.util.List;

public interface AwardRepositoryCustom {
    List<Award> findByClubOrderBy(Club club, String order);
}
