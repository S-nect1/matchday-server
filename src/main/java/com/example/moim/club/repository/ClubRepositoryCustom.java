package com.example.moim.club.repository;

import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.entity.Club;

import java.util.List;

public interface ClubRepositoryCustom {
    List<Club> findBySearchCond(ClubSearchCond clubSearchCond);
}
