package com.example.moim.club.service;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSearchOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.user.entity.User;

import java.io.IOException;
import java.util.List;

public interface ClubQueryService {
    List<ClubSearchOutput> searchClub(ClubSearchCond clubSearchCond);
    ClubOutput findClub(Long id, User user);
}
