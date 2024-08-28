package com.example.moim.match.service;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ScheduleRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.club.service.ScheduleService;
import com.example.moim.exception.match.MatchPermissionException;
import com.example.moim.match.dto.*;
import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchApplication;
import com.example.moim.match.entity.MatchStatus;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.notification.dto.MatchAppVoteEvent;
import com.example.moim.notification.dto.MatchRequestEvent;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    private final MatchApplicationRepository matchApplicationRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MatchOutput saveMatch(User user, MatchInput matchInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchPermissionException("매치 생성 권한이 없습니다.");
        }

        if (matchInput.getFee() == 0) {
            Optional<Integer> fee = matchRepository.findFeeByClubIdAndLocation(matchInput.getClubId(), matchInput.getLocation()).stream().findFirst();
            if (fee.isPresent()) {
                matchInput.setFee(fee.get());
            } else {
                throw new MatchPermissionException("대관비를 입력해주세요.");
            }
        }

        if (matchInput.getAccount() == null || matchInput.getAccount().isEmpty()) {
            Optional<String> clubAccount = matchRepository.findAccountByClubId(matchInput.getClubId()).stream().findFirst();
            if (clubAccount.isPresent()) {
                matchInput.setAccount(clubAccount.get());
            } else {
                throw new MatchPermissionException("계좌번호를 입력해주세요.");
            }
        }

        Match match = matchRepository.save(Match.createMatch(clubRepository.findById(matchInput.getClubId()).get(), matchInput));

        //일정에 매치 등록
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(clubRepository.findById(matchInput.getClubId()).get(), match.createScheduleFromMatch()));
        match.setSchedule(schedule);
        matchRepository.save(match);


        return new MatchOutput(match.getId());
    }

    @Transactional
    public MatchRegOutput registerMatch(User user, MatchRegInput matchRegInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchRegInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchPermissionException("매치 생성 권한이 없습니다.");
        }

        Match match = matchRepository.findById(matchRegInput.getId()).get();

        if (match.getSchedule().getAttend() < match.getMinParticipants()) {
            match.failMatch();
            matchRepository.save(match);
            return null;
        }
        match.completeMatch(matchRegInput);
        matchRepository.save(match);

        return new MatchRegOutput(match.getId());
    }

    public MatchApplyOutput saveMatchApp(User user, Long matchId, Long clubId) {
        Club awayClub = clubRepository.findById(clubId).get();
        Match match = matchRepository.findById(matchId).get();

        UserClub userClub = userClubRepository.findByClubAndUser(awayClub, user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            eventPublisher.publishEvent(new MatchRequestEvent(match, user));
            return null;
        }

        MatchApplication existingApplication = matchApplicationRepository.findByMatchAndClub(match, awayClub);
        if (existingApplication != null) {
            throw new MatchPermissionException("이미 신청한 매치입니다.");
        }

        MatchApplication matchApplication = matchApplicationRepository.save(MatchApplication.applyMatch(match, awayClub));
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(matchApplication.getClub(), matchApplication.getMatch().createScheduleFromMatch()));

        matchApplication.setSchedule(schedule);
        matchApplicationRepository.save(matchApplication);

        return new MatchApplyOutput(matchApplication.getId());
    }

    public MatchApplyOutput applyMatch(User user, MatchApplyInput matchApplyInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchApplyInput.getClubId()).get(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchPermissionException("매치 신청 등록 권한이 없습니다.");
        }

        MatchApplication matchApplication = matchApplicationRepository.findById(matchApplyInput.getId()).get();

        if (matchApplication.getSchedule().getAttend() < matchApplication.getMatch().getMinParticipants()) {
            matchApplication.failApply();
            matchApplicationRepository.save(matchApplication);
            return null;
        }

        matchApplication.completeApplication(matchApplyInput);
        matchApplicationRepository.save(matchApplication);

        return new MatchApplyOutput(matchApplication.getId());
    }

    public List<ConfirmedMatchOutput> findConfirmedMatch(Club club) {
        return matchRepository.findConfirmedMatchByClub(club).stream().map(ConfirmedMatchOutput::new).toList();
    }

    public MatchConfirmOutput confirmMatch(Long id, Long awayClubId, User user) {
        Match match = matchRepository.findById(id).get();
        Club awayClub = clubRepository.findById(awayClubId).get();

        UserClub userClub = userClubRepository.findByClubAndUser(match.getHomeClub(), user).get();
        if (!(userClub.getCategory().equals("creator") || userClub.getCategory().equals("admin"))) {
            throw new MatchPermissionException("매치 확정 권한이 없습니다.");
        }

        MatchApplication matchApplication = matchApplicationRepository
                .findByMatchAndClub(match, awayClub);
        match.confirmMatch(awayClub);
        matchApplication.confirmMatch();

        matchRepository.save(match);
        matchApplicationRepository.save(matchApplication);

        List<MatchApplication> rejectedApply = matchApplicationRepository.findRejectedByMatch(match);
        for (MatchApplication application : rejectedApply) {
            application.rejectMatch();
            matchApplicationRepository.save(application);
        }

        return new MatchConfirmOutput(awayClub);
    }

//    public List<RegMatchOutput> findRegMatch(Club club) {
//        return matchRepository.findMatchByClub(club).stream().map(m -> new RegMatchOutput()).toList();
//    }

    public List<MatchSearchOutput> searchMatch(MatchSearchCond matchSearchCond) {
        return matchRepository.findBySearchCond(matchSearchCond).stream()
                .map(MatchSearchOutput::new).toList();
    }

    public List<MatchStatusOutput> findMatchStatus(Club club) {

        return (matchRepository.findMatchByClub(club) != null) ? matchRepository.findMatchByClub(club).stream().map(m -> new MatchStatusOutput(m)).toList() : Collections.emptyList();
    }

}
