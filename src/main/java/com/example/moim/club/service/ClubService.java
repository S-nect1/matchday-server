package com.example.moim.club.service;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.dto.ClubOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;

    public ClubOutput saveClub(User user, ClubInput clubInput) {
        Club club = clubRepository.save(Club.createClub(clubInput));
        userClubRepository.save(UserClub.createLeaderUserClub(user, club));
        return new ClubOutput(club);
    }
}
