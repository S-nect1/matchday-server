package com.example.moim.match.service;

import com.example.moim.club.entity.Club;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.exception.advice.MatchControllerAdvice;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.repository.ScheduleVoteRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.schedule.service.ScheduleService;
import com.example.moim.match.dto.*;
import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchApplication;
import com.example.moim.match.entity.MatchUser;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.match.repository.MatchUserRepository;
import com.example.moim.notification.dto.MatchInviteEvent;
import com.example.moim.notification.dto.MatchRequestEvent;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserClubRepository userClubRepository;
    private final ClubRepository clubRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    private final MatchApplicationRepository matchApplicationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MatchUserRepository matchUserRepository;
    private final ScheduleVoteRepository scheduleVoteRepository;

    // PENDING
    @Transactional
    public MatchOutput saveMatch(User user, MatchInput matchInput) {
        Club club = clubRepository.findById(matchInput.getClubId())
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
//            throw new MatchPermissionException("매치 생성 권한이 없습니다.");
            throw new MatchControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        matchRepository.findMatchByClub(club).forEach(m -> m.timeDuplicationCheck(matchInput.getStartTime(), matchInput.getEndTime()));

        if (matchInput.getFee() == 0) {
            Optional<Integer> fee = matchRepository.findFeeByClubIdAndLocation(matchInput.getClubId(), matchInput.getLocation()).stream().findFirst();
            if (fee.isPresent()) {
                matchInput.setFee(fee.get());
            } else {
//                throw new MatchPermissionException("대관비를 입력해주세요.");
                throw new MatchControllerAdvice(ResponseCode.MATCH_REQUIRE_FEE);
            }
        }

        if (matchInput.getAccount() == null || matchInput.getAccount().isEmpty()) {
            Optional<String> clubAccount = matchRepository.findAccountByClubId(matchInput.getClubId()).stream().findFirst();
            if (clubAccount.isPresent()) {
                matchInput.setAccount(clubAccount.get());
            } else {
//                throw new MatchPermissionException("계좌번호를 입력해주세요.");
                throw new MatchControllerAdvice(ResponseCode.MATCH_REQUIRE_ACCOUNT);
            }
        }

        Match match = matchRepository.save(Match.createMatch(clubRepository.findById(matchInput.getClubId())
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), matchInput));

        //일정에 매치 등록
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(clubRepository.findById(matchInput.getClubId())
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), match.createScheduleFromMatch()));
        match.setSchedule(schedule);
        matchRepository.save(match);

        return new MatchOutput(match.getId());
    }

    // FAILED or REGISTERED
    @Transactional
    public MatchRegOutput registerMatch(User user, MatchRegInput matchRegInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchRegInput.getClubId())
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
//            throw new MatchPermissionException("매치 생성 권한이 없습니다.");
            throw new MatchControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        Match match = matchRepository.findById(matchRegInput.getId()).orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_NOT_FOUND));

        if (match.getSchedule().getAttend() < match.getMinParticipants()) {
            match.failMatch();
            matchRepository.save(match);
            return null;
        }
        match.completeMatch(matchRegInput);
        matchRepository.save(match);

        return new MatchRegOutput(match.getId());
    }

    // PENDING_APP
    @Transactional
    public MatchApplyOutput saveMatchApp(User user, Long matchId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_NOT_FOUND));

        UserClub userClub = userClubRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
            eventPublisher.publishEvent(new MatchRequestEvent(match, user, club));
            return null;
        }

        MatchApplication existingApplication = matchApplicationRepository.findByMatchAndClub(match, club);
        if (existingApplication != null) {
//            throw new MatchPermissionException("이미 신청한 매치입니다.");
            throw new MatchControllerAdvice(ResponseCode.MATCH_ALREADY_FOUND);
        }
        matchRepository.findMatchByClub(club).forEach(m -> m.timeDuplicationCheck(match.getStartTime(), match.getEndTime()));

        MatchApplication matchApplication = matchApplicationRepository.save(MatchApplication.applyMatch(match, club));
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(matchApplication.getClub(), matchApplication.getMatch().createScheduleFromMatch()));

        matchApplication.setSchedule(schedule);
        matchApplicationRepository.save(matchApplication);

        return new MatchApplyOutput(matchApplication.getId());
    }

    // REJECTED or APP_COMPLETED
    @Transactional
    public MatchApplyOutput applyMatch(User user, MatchApplyInput matchApplyInput) {
        UserClub userClub = userClubRepository.findByClubAndUser(clubRepository.findById(matchApplyInput.getClubId())
                        .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
//            throw new MatchPermissionException("매치 신청 등록 권한이 없습니다.");
            throw new MatchControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        MatchApplication matchApplication = matchApplicationRepository.findById(matchApplyInput.getId())
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_APPLICATION_NOT_FOUND));

        if (matchApplication.getSchedule().getAttend() < matchApplication.getMatch().getMinParticipants()) {
            matchApplication.failApply();
            matchApplicationRepository.save(matchApplication);
            return null;
        }

        matchApplication.completeApplication(matchApplyInput);
        matchApplicationRepository.save(matchApplication);

        return new MatchApplyOutput(matchApplication.getId());
    }

    // 매치 초대
    public void inviteMatch(User user, Long matchId, Long clubId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_NOT_FOUND));
        UserClub userClub = userClubRepository
                .findByClubAndUser(clubRepository.findById(match.getHomeClub().getId())
                        .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
//            throw new MatchPermissionException("매치 초청 권한이 없습니다.");
            throw new MatchControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        eventPublisher.publishEvent(new MatchInviteEvent(match, clubRepository.findById(clubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND)), user));
    }

    // 매치 확정 -> CONFIRMED
    public MatchConfirmOutput confirmMatch(Long id, Long awayClubId, User user) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_NOT_FOUND));
        Club awayClub = clubRepository.findById(awayClubId)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND));

        UserClub userClub = userClubRepository.findByClubAndUser(match.getHomeClub(), user)
                .orElseThrow(() -> new MatchControllerAdvice(ResponseCode.MATCH_USER_NOT_FOUND));
        if (!(userClub.getClubRole().equals(ClubRole.PRESIDENT) || userClub.getClubRole().equals(ClubRole.VICE_PRESIDENT))) {
//            throw new MatchPermissionException("매치 확정 권한이 없습니다.");
            throw new MatchControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        match.confirmMatch(awayClub);
        saveMatchUserByAttendance(match, match.getSchedule());

        MatchApplication matchApplication = matchApplicationRepository.findByMatchAndClub(match, awayClub);
        matchApplication.confirmMatch();
        saveMatchUserByAttendance(matchApplication.getMatch(), matchApplication.getSchedule());

        matchRepository.save(match);
        matchApplicationRepository.save(matchApplication);

        List<MatchApplication> rejectedApply = matchApplicationRepository.findRejectedByMatch(match);
        for (MatchApplication application : rejectedApply) {
            application.rejectMatch();
            matchApplicationRepository.save(application);
        }

        return new MatchConfirmOutput(awayClub);
    }

    //유저가 친선 매치 일정에 참여 투표 시 매치 유저 저장, 수정 필요 -> 문제 있나?
    private void saveMatchUserByAttendance(Match match, Schedule schedule) {
        for (ScheduleVote scheduleVote : scheduleVoteRepository.findBySchedule(schedule)) {
            if (scheduleVote.getAttendance().equals("attend")) {
                log.info("userid:{}", scheduleVote.getUser().getId());
                MatchUser matchUser = MatchUser.createMatchUser(match, scheduleVote);
                matchUserRepository.save(matchUser);
            }
        }
    }

    public List<MatchSearchOutput> searchMatch(MatchSearchCond matchSearchCond) {

        return matchRepository.findBySearchCond(matchSearchCond).stream()
                .map(MatchSearchOutput::new)
                .toList();
    }

    public List<ConfirmedMatchOutput> findConfirmedMatch(Club club) {

        return matchRepository.findConfirmedMatchByClub(club).stream()
                .map(match -> new ConfirmedMatchOutput(match, club))
                .toList();
    }

    public List<MatchClubOutput> searchMatchClubs(MatchClubSearchCond matchClubSearchCond, Club club) {
        List<Club> matchClubs = matchRepository.findClubsBySearchCond(matchClubSearchCond);

        return matchClubs.stream()
                .filter(c -> c.getActivityArea().equals(club.getActivityArea()))
                .filter(c -> !c.getId().equals(club.getId()))
                .map(MatchClubOutput::new).toList();
    }

    public List<MatchStatusOutput> findMatchStatus(Club club) {

        return (matchRepository.findMatchByClub(club) != null) ? matchRepository.findMatchByClub(club).stream()
                .map(MatchStatusOutput::new).toList() : Collections.emptyList();
    }

    @Transactional
    public MatchRecordOutput saveMatchRecord(Match match, User user, MatchRecordInput matchRecordInput) {
        //매치 종료 48시간 이후면 점수 기록 못하게
        if (LocalDateTime.now().isAfter(match.getEndTime().plusHours(48))) {
//            throw new MatchRecordExpireException();
            throw new MatchControllerAdvice(ResponseCode.MATCH_TIME_OUT);
        }
        MatchUser matchUser = matchUserRepository.findByMatchAndUser(match, user);
        matchUser.recordScore(matchRecordInput);
        return new MatchRecordOutput(matchUser);
    }

    public MatchMainOutput matchMainFind(Long clubId) {
        Club myClub = clubRepository.findById(clubId).orElseThrow(() -> new MatchControllerAdvice(ResponseCode.CLUB_NOT_FOUND));

        List<Match> matches = matchRepository.findRegisteredMatch(myClub);
        String[] myClubCoordinate = myClub.getUniversity().replace("@", ",").split(",");
        List<RegisteredMatchDto> registeredMatchDtos = new ArrayList<>(matches.size());

        for (Match match : matches) {
            String[] matchCoordinate = match.getLocation().replace("@", ",").split(",");
            registeredMatchDtos.add(new RegisteredMatchDto(match, distance(Double.parseDouble(myClubCoordinate[2]), Double.parseDouble(myClubCoordinate[1]),
                            Double.parseDouble(matchCoordinate[2]), Double.parseDouble(matchCoordinate[1]))));
        }
        Collections.sort(registeredMatchDtos);
        return new MatchMainOutput(myClub.getTitle(), clubRepository.findByActivityArea(myClub.getActivityArea()).stream().map(NearClubsOutput::new).toList(), registeredMatchDtos);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
