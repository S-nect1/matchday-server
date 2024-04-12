package com.example.moim.club.service;

import com.example.moim.club.dto.*;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.AwardRepository;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ScheduleRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;
    private final ScheduleRepository scheduleRepository;
    private final AwardRepository awardRepository;

    public ClubOutput saveClub(User user, ClubInput clubInput) {
        Club club = clubRepository.save(Club.createClub(clubInput));
        userClubRepository.save(UserClub.createLeaderUserClub(user, club));
        return new ClubOutput(club);
    }

    public ClubOutput findClub(Long id) {
        Club club = clubRepository.findById(id).get();
        List<UserClubOutput> userClubOutputs = userClubRepository.findAllByClub(club).stream().map(UserClubOutput::new).toList();
        List<ScheduleOutput> scheduleOutputs = scheduleRepository.findTop5ByClub(club).stream().map(ScheduleOutput::new).toList();
        List<AwardOutput> awardOutputs = awardRepository.findByClub(club).stream().map(AwardOutput::new).toList();
        return new ClubOutput(club, userClubOutputs, scheduleOutputs, awardOutputs);
    }
}
