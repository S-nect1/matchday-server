package com.example.moim.match.service;

import com.example.moim.club.dto.*;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.exception.club.ClubPermissionException;
import com.example.moim.exception.match.MatchSaveException;
import com.example.moim.match.dto.MatchInput;
import com.example.moim.match.dto.MatchOutput;
import com.example.moim.match.dto.MatchSearchCond;
import com.example.moim.match.dto.MatchSearchOutput;
import com.example.moim.match.entity.Match;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;

    public MatchOutput saveMatch(User user, MatchInput matchInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchSaveException("매치 등록 권한이 없습니다.");
        }

        Match match = matchRepository.save(Match.createMatch(clubRepository.findById(matchInput.getClubId()).get(), matchInput));
        return new MatchOutput(match.getId());
    }

    public List<MatchSearchOutput> searchMatch(MatchSearchCond matchSearchCond) {
        return matchRepository.findBySearchCond(matchSearchCond).stream()
                .map(MatchSearchOutput::new).toList();
    }

}
