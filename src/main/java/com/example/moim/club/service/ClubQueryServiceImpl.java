package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSearchOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubQueryServiceImpl implements ClubQueryService {

    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;

    public List<ClubSearchOutput> searchClub(ClubSearchCond clubSearchCond) {
        return clubRepository.findBySearchCond(clubSearchCond).stream().map(ClubSearchOutput::new).toList();
    }

    public ClubOutput findClub(Long id, User user) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        // 비회원과 회원일 경우를 나눠서 데이터를 주려고 한듯!
        Optional<UserClub> userClub = userClubRepository.findByClubAndUser(club, user);
        if (userClub.isPresent()) {
            List<UserClubOutput> userClubOutputs = userClubRepository.findAllByClub(club).stream().map(UserClubOutput::new).toList();
//            List<ScheduleOutput> scheduleOutputs = scheduleRepository.findTop5ByClubOrderByCreatedDateDesc(club).stream().map(ScheduleOutput::new).toList();
//            List<AwardOutput> awardOutputs = awardRepository.findByClub(club).stream().map(AwardOutput::new).toList();
            return new ClubOutput(club, userClubOutputs, userClub.get().getClubRole());
        }

        return new ClubOutput(club, null, null);
    }
}
