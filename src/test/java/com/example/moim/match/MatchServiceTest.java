package com.example.moim.match;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.match.dto.*;
import com.example.moim.match.entity.*;
import com.example.moim.match.exception.MatchPermissionException;
import com.example.moim.match.exception.MatchRecordExpireException;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.match.repository.MatchRepository;
import com.example.moim.match.repository.MatchUserRepository;
import com.example.moim.match.service.MatchService;
import com.example.moim.notification.dto.MatchInviteEvent;
import com.example.moim.notification.dto.MatchRequestEvent;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.repository.ScheduleVoteRepository;
import com.example.moim.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleVoteRepository scheduleVoteRepository;
    @Mock
    private MatchApplicationRepository matchApplicationRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private MatchUserRepository matchUserRepository;

    @InjectMocks
    private MatchService matchService;

    private Club club;
    private User user;

    private UserClub newUserClub;

    private UserClub leaderUserClub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ClubInput clubInput = new ClubInput();
        clubInput.setTitle("Test Club");
        clubInput.setExplanation("explanation");
        clubInput.setIntroduction("introduction");
        clubInput.setClubCategory(ClubCategory.SCHOOL_GROUP.getKoreanName());
        clubInput.setUniversity("TestUniv@37.5665@126.9780");
        clubInput.setGender(Gender.MAN.getKoreanName());
        clubInput.setActivityArea(ActivityArea.SEOUL.getKoreanName());
        clubInput.setAgeRange(AgeRange.TWENTIES.getKoreanName());
        clubInput.setSportsType(SportsType.SOCCER.getKoreanName());
        clubInput.setClubPassword("password");
        clubInput.setMainUniformColor("흰색");
        clubInput.setSubUniformColor("검은색");

        club = Club.createClub(clubInput, null);
        ReflectionTestUtils.setField(club, "id", 1L);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        newUserClub = UserClub.createUserClub(user, club);

        leaderUserClub = UserClub.createLeaderUserClub(user, club);
    }

    // saveMatch - 정상 흐름
    @Test
    void testSaveMatch_Success() {
        MatchInput input = new MatchInput();
        input.setClubId(1L);
        input.setStartTime(LocalDateTime.now().plusDays(1));
        input.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
        input.setFee(5000);
        input.setLocation("test");
        input.setAccount("");
        input.setEvent("축구");
        input.setMatchSize("11");
        input.setMinParticipants(10);
        input.setBank("신한");

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchRepository.findMatchByClub(club)).thenReturn(Collections.emptyList());
        when(matchRepository.findFeeByClubIdAndLocation(1L, "test")).thenReturn(Arrays.asList(5000));
        when(matchRepository.findAccountByClubId(1L)).thenReturn(Arrays.asList("123-456-789"));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> {
            Match m = invocation.getArgument(0);
            ReflectionTestUtils.setField(m, "id", 100L);
            return m;
        });
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MatchOutput output = matchService.saveMatch(user, input);
        assertNotNull(output);
        assertEquals(100L, output.getId());
        verify(matchRepository, atLeast(2)).save(any(Match.class));
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    // saveMatch - 권한 부족(creator가 아닌 유저)
    @Test
    void testSaveMatch_NoPermission() {
        MatchInput input = new MatchInput();
        input.setClubId(1L);
        input.setStartTime(LocalDateTime.now().plusDays(1));
        input.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
        input.setFee(0);
        input.setLocation("test");
        input.setAccount("");

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(newUserClub));

        assertThrows(MatchPermissionException.class, () -> matchService.saveMatch(user, input));
    }

    // registerMatch - 정상 플로우 (참가 인원 충족)
    @Test
    void testRegisterMatch_Success() {
        MatchRegInput regInput = new MatchRegInput();
        regInput.setId(200L);
        regInput.setClubId(1L);
        regInput.setBall(true);
        regInput.setNote("test note");

        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 1L);
        ReflectionTestUtils.setField(match, "minParticipants", 5);
        Schedule schedule = new Schedule();
        ReflectionTestUtils.setField(schedule, "attend", 5);
        ReflectionTestUtils.setField(match, "schedule", schedule);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchRepository.findById(200L)).thenReturn(Optional.of(match));

        MatchRegOutput output = matchService.registerMatch(user, regInput);
        assertNotNull(output);
        verify(matchRepository, times(1)).save(match);
        assertEquals(MatchStatus.REGISTERED, match.getMatchStatus());
    }

    // registerMatch - 참가 인원 부족 시 실패 처리 (null 반환 및 상태 FAILED)
    @Test
    void testRegisterMatch_InsufficientAttendance() {
        MatchRegInput regInput = new MatchRegInput();
        regInput.setId(201L);
        regInput.setClubId(1L);
        regInput.setBall(true);
        regInput.setNote("Insufficient attendance");

        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 201L);
        ReflectionTestUtils.setField(match, "minParticipants", 5);
        Schedule schedule = new Schedule();
        ReflectionTestUtils.setField(schedule, "attend", 3);
        ReflectionTestUtils.setField(match, "schedule", schedule);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchRepository.findById(201L)).thenReturn(Optional.of(match));

        MatchRegOutput output = matchService.registerMatch(user, regInput);
        assertNull(output);
        assertEquals(MatchStatus.FAILED, match.getMatchStatus());
        verify(matchRepository, times(1)).save(match);
    }

    // saveMatchApp - 정상 플로우 (신청 생성)
    @Test
    void testSaveMatchApp_Success() {
        Long matchId = 300L;
        Long clubId = 1L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);

        MatchApplication app = new MatchApplication();
        ReflectionTestUtils.setField(app, "id", 400L);

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchApplicationRepository.findByMatchAndClub(match, club)).thenReturn(null);
        when(matchRepository.findMatchByClub(club)).thenReturn(Collections.emptyList());
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(matchApplicationRepository.save(any(MatchApplication.class))).thenAnswer(invocation -> {
            MatchApplication a = invocation.getArgument(0);
            ReflectionTestUtils.setField(a, "id", 400L);
            return a;
        });
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MatchApplyOutput output = matchService.saveMatchApp(user, matchId, clubId);
        assertNotNull(output);
        assertEquals(400L, output.getId());
    }

    // saveMatchApp - 이미 신청된 경우 예외 발생
    @Test
    void testSaveMatchApp_AlreadyApplied() {
        Long matchId = 301L;
        Long clubId = 1L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);
        MatchApplication app = new MatchApplication();
        ReflectionTestUtils.setField(app, "id", 401L);

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchApplicationRepository.findByMatchAndClub(match, club)).thenReturn(app);

        assertThrows(MatchPermissionException.class, () -> matchService.saveMatchApp(user, matchId, clubId));
    }

    // saveMatchApp - 권한 없는 경우 이벤트 발행 후 null 반환
    @Test
    void testSaveMatchApp_NoPermission() {
        Long matchId = 302L;
        Long clubId = 1L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(newUserClub));

        MatchApplyOutput output = matchService.saveMatchApp(user, matchId, clubId);
        assertNull(output);
        verify(eventPublisher, times(1)).publishEvent(any(MatchRequestEvent.class));
    }

    // applyMatch - 정상 플로우
    @Test
    void testApplyMatch_Success() {
        MatchApplyInput applyInput = new MatchApplyInput();
        applyInput.setId(500L);
        applyInput.setClubId(1L);
        applyInput.setBall(true);
        applyInput.setNote("Apply success");

        MatchApplication app = new MatchApplication();
        ReflectionTestUtils.setField(app, "id", 500L);
        Schedule schedule = new Schedule();
        ReflectionTestUtils.setField(schedule, "attend", 10);
        ReflectionTestUtils.setField(app, "schedule", schedule);
        Match match = new Match();
        ReflectionTestUtils.setField(match, "minParticipants", 5);
        ReflectionTestUtils.setField(app, "match", match);

        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(matchApplicationRepository.findById(500L)).thenReturn(Optional.of(app));

        MatchApplyOutput output = matchService.applyMatch(user, applyInput);
        assertNotNull(output);
        assertEquals(500L, output.getId());
    }

    // applyMatch - 참석 인원 부족 시 실패 처리
    @Test
    void testApplyMatch_InsufficientAttendance() {
        MatchApplyInput applyInput = new MatchApplyInput();
        applyInput.setId(501L);
        applyInput.setClubId(1L);
        applyInput.setBall(true);
        applyInput.setNote("test note");

        MatchApplication app = new MatchApplication();
        ReflectionTestUtils.setField(app, "id", 501L);
        Schedule schedule = new Schedule();
        ReflectionTestUtils.setField(schedule, "attend", 3);
        ReflectionTestUtils.setField(app, "schedule", schedule);
        Match match = new Match();
        ReflectionTestUtils.setField(match, "minParticipants", 5);
        ReflectionTestUtils.setField(app, "match", match);

        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(matchApplicationRepository.findById(501L)).thenReturn(Optional.of(app));

        MatchApplyOutput output = matchService.applyMatch(user, applyInput);  // null 반환
        assertNull(output);
    }

    // applyMatch - 권한 부족
    @Test
    void testApplyMatch_NoPermission() {
        MatchApplyInput applyInput = new MatchApplyInput();
        applyInput.setId(502L);
        applyInput.setClubId(1L);
        applyInput.setBall(true);
        applyInput.setNote("test note");

        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(newUserClub));
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));

        assertThrows(MatchPermissionException.class, () -> matchService.applyMatch(user, applyInput));
    }

    // inviteMatch - 정상 플로우
    @Test
    void testInviteMatch_Success() {
        Long matchId = 600L;
        Long clubId = 1L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);
        ReflectionTestUtils.setField(match, "homeClub", club);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(clubRepository.findById(match.getHomeClub().getId())).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));

        assertDoesNotThrow(() -> matchService.inviteMatch(user, matchId, clubId));
        verify(eventPublisher, times(1)).publishEvent(any(MatchInviteEvent.class));
    }

    // inviteMatch - 권한 부족
    @Test
    void testInviteMatch_NoPermission() {
        Long matchId = 601L;
        Long clubId = 1L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);
        ReflectionTestUtils.setField(match, "homeClub", club);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(clubRepository.findById(match.getHomeClub().getId())).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(newUserClub));

        assertThrows(MatchPermissionException.class, () -> matchService.inviteMatch(user, matchId, clubId));
    }

    // confirmMatch - 정상 플로우
    @Test
    void testConfirmMatch_Success() {
        Long matchId = 700L;
        Long awayClubId = 2L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);
        ReflectionTestUtils.setField(match, "homeClub", club);
        Club awayClub = new Club();
        ReflectionTestUtils.setField(awayClub, "id", awayClubId);
        MatchApplication app = new MatchApplication();
        ReflectionTestUtils.setField(app, "id", 800L);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(clubRepository.findById(awayClubId)).thenReturn(Optional.of(awayClub));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(leaderUserClub));
        when(matchApplicationRepository.findByMatchAndClub(match, awayClub)).thenReturn(app);

        MatchConfirmOutput output = matchService.confirmMatch(matchId, awayClubId, user);
        assertNotNull(output);
        assertEquals(awayClub, match.getAwayClub());
        assertEquals(MatchStatus.CONFIRMED, match.getMatchStatus());
        verify(matchRepository, times(1)).save(match);
        verify(matchApplicationRepository, times(1)).save(app);
    }

    // confirmMatch - 권한 부족
    @Test
    void testConfirmMatch_NoPermission() {
        Long matchId = 701L;
        Long awayClubId = 2L;
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", matchId);
        ReflectionTestUtils.setField(match, "homeClub", club);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(clubRepository.findById(awayClubId)).thenReturn(Optional.of(new Club()));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(newUserClub));

        assertThrows(MatchPermissionException.class, () -> matchService.confirmMatch(matchId, awayClubId, user));
    }

    // saveMatchRecord - 정상 플로우
    @Test
    void testSaveMatchRecord_Success() {
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 900L);
        ReflectionTestUtils.setField(match, "endTime", LocalDateTime.now().minusHours(24)); // 48시간 이내
        MatchUser matchUser = new MatchUser();
        ReflectionTestUtils.setField(matchUser, "id", 950L);
        ReflectionTestUtils.setField(matchUser, "score", null);
        ReflectionTestUtils.setField(matchUser, "user", user);

        when(matchUserRepository.findByMatchAndUser(match, user)).thenReturn(matchUser);

        MatchRecordInput recordInput = new MatchRecordInput();
        recordInput.setScore(10);
        MatchRecordOutput output = matchService.saveMatchRecord(match, user, recordInput);
        assertNotNull(output);
        assertEquals(10, ReflectionTestUtils.getField(matchUser, "score"));
    }

    // saveMatchRecord - 기록 만료 (48시간 초과)
    @Test
    void testSaveMatchRecord_Expired() {
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 901L);
        ReflectionTestUtils.setField(match, "endTime", LocalDateTime.now().minusHours(49));
        MatchRecordInput recordInput = new MatchRecordInput();
        recordInput.setScore(10);
        assertThrows(MatchRecordExpireException.class, () -> matchService.saveMatchRecord(match, user, recordInput));
    }

    // searchMatch - 조회 기능 테스트
    @Test
    void testSearchMatch() {
        MatchSearchCond cond = new MatchSearchCond();
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 1000L);
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 10, 15, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 10, 17, 0);
        ReflectionTestUtils.setField(match, "startTime", startTime);
        ReflectionTestUtils.setField(match, "endTime", endTime);

        when(matchRepository.findBySearchCond(cond)).thenReturn(Arrays.asList(match));

        List<MatchSearchOutput> list = matchService.searchMatch(cond);
        assertFalse(list.isEmpty());
        assertEquals(1000L, list.get(0).getId());
    }

    // findConfirmedMatch - 조회 기능 테스트
    @Test
    void testFindConfirmedMatch() {
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 1100L);
        ReflectionTestUtils.setField(match, "homeClub", club);

        // awayClub 생성 및 설정
        Club opponentClub = new Club();
        ReflectionTestUtils.setField(opponentClub, "id", 2L);
        ReflectionTestUtils.setField(opponentClub, "title", "Opponent Club");

        ReflectionTestUtils.setField(match, "awayClub", opponentClub);
        ReflectionTestUtils.setField(match, "event", "축구");
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 10, 15, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 10, 17, 0);
        ReflectionTestUtils.setField(match, "startTime", startTime);
        ReflectionTestUtils.setField(match, "endTime", endTime);
        ReflectionTestUtils.setField(match, "location", "test location");

        when(matchRepository.findConfirmedMatchByClub(club)).thenReturn(Arrays.asList(match));

        // 3. 서비스 메서드 호출: club 기준으로 ConfirmedMatchOutput 리스트 반환
        List<ConfirmedMatchOutput> list = matchService.findConfirmedMatch(club);

        // 4. 검증: 리스트가 비어있지 않은지, DTO의 필드 값들이 올바른지 확인
        assertFalse(list.isEmpty());

        ConfirmedMatchOutput output = list.get(0);
        // OpponentClubName은 match.findOpponentClub(club).getTitle()로 결정됨.
        // homeClub은 club, awayClub은 opponentClub이므로, opponentClub의 title이 반환되어야 함.
        assertEquals("Opponent Club", output.getOpponentClubName());
        assertEquals("축구", output.getEvent());
        assertEquals(LocalDate.of(2025, 5, 10), output.getMatchDate());
        assertEquals("test location", output.getLocation());

        // period는 startTime과 endTime의 toLocalTime().toString()을 이용해 "15:00 ~ 17:00" 형식으로 생성됨
        String expectedPeriod = "15:00 ~ 17:00";
        assertEquals(expectedPeriod, output.getPeriod());
    }

    // searchMatchClubs - 검색 기능 테스트
    @Test
    void testSearchMatchClubs() {
        MatchClubSearchCond cond = new MatchClubSearchCond();
        Club otherClub = new Club();
        ReflectionTestUtils.setField(otherClub, "id", 2L);
        ReflectionTestUtils.setField(otherClub, "activityArea", ActivityArea.fromKoreanName("서울").get());
        ReflectionTestUtils.setField(otherClub, "ageRange", AgeRange.fromKoreanName("20대").get());
        ReflectionTestUtils.setField(otherClub, "gender", Gender.fromKoreanName("혼성").get());

        when(matchRepository.findClubsBySearchCond(cond)).thenReturn(Arrays.asList(otherClub));
        List<MatchClubOutput> list = matchService.searchMatchClubs(cond, club);
        assertFalse(list.isEmpty());
        assertEquals(2L, list.get(0).getClubId());
    }

    // findMatchStatus - 조회 기능 테스트
    @Test
    void testFindMatchStatus() {
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 1200L);
        LocalDateTime startTime = LocalDateTime.of(2025, 3, 2, 15, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 10, 17, 0);
        ReflectionTestUtils.setField(match, "startTime", startTime);
        ReflectionTestUtils.setField(match, "endTime", endTime);

        when(matchRepository.findMatchByClub(club)).thenReturn(Arrays.asList(match));

        List<MatchStatusOutput> list = matchService.findMatchStatus(club);
        assertFalse(list.isEmpty());
        assertEquals(1200L, list.get(0).getId());
    }

    // matchMainFind - 대시보드 기능 테스트 (거리 계산 포함)
    @Test
    void testMatchMainFind() {
        Match match = new Match();
        ReflectionTestUtils.setField(match, "id", 1300L);
        ReflectionTestUtils.setField(match, "location", "Test@126.9780@37.5665");
        ReflectionTestUtils.setField(match, "homeClub", club);
        LocalDateTime startTime = LocalDateTime.of(2025, 3, 2, 15, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 10, 17, 0);
        ReflectionTestUtils.setField(match, "startTime", startTime);
        ReflectionTestUtils.setField(match, "endTime", endTime);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(matchRepository.findRegisteredMatch(club)).thenReturn(Arrays.asList(match));
        when(clubRepository.findByActivityArea(club.getActivityArea())).thenReturn(Arrays.asList(club));

        MatchMainOutput output = matchService.matchMainFind(1L, user);
        assertNotNull(output);
        assertEquals("Test Club", output.getClubTitle());
    }
}
