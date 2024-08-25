package com.example.moim.match.service;

import com.example.moim.club.dto.ScheduleInput;
import com.example.moim.club.dto.ScheduleOutput;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ScheduleRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.club.service.ScheduleService;
import com.example.moim.exception.match.MatchSaveException;
import com.example.moim.match.dto.*;
import com.example.moim.match.entity.Match;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    public MatchOutput saveMatch(User user, MatchInput matchInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchSaveException("매치 생성 권한이 없습니다.");
        }

        Match match = matchRepository.save(Match.createMatch(clubRepository.findById(matchInput.getClubId()).get(), matchInput));

        //일정에 매치 등록
        ScheduleOutput scheduleOutput = scheduleService.saveSchedule(createScheduleFromMatch(match), user);
        match.setSchedule(scheduleRepository.findScheduleById(scheduleOutput.getId()));
        matchRepository.save(match);

        return new MatchOutput(match.getId());
    }

    public MatchRegOutput registerMatch(User user, MatchRegInput matchRegInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchRegInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchSaveException("매치 생성 권한이 없습니다.");
        }

        Match match = matchRepository.findById(matchRegInput.getId()).get();

        if (match.getSchedule().getAttend() < match.getMinParticipants()) {
            match.failMatch();
            matchRepository.save(match);
            return new MatchRegOutput(match.getId());
        }
        match.completeMatch(matchRegInput);
        matchRepository.save(match);

        return new MatchRegOutput(match.getId());
    }

    public List<MatchSearchOutput> searchMatch(MatchSearchCond matchSearchCond) {
        return matchRepository.findBySearchCond(matchSearchCond).stream()
                .map(MatchSearchOutput::new).toList();
    }

    private ScheduleInput createScheduleFromMatch(Match match) {
        return new ScheduleInput(
                match.getId(),
                match.getName(),
                match.getLocation(),
                match.getStartTime(),
                match.getEndTime(),
                match.getMinParticipants(),
                "친선 매치",
                match.getNote());
    }
}
