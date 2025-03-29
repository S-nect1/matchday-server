//package com.example.moim.match;
//
//import com.example.moim.club.entity.Club;
//import com.example.moim.match.entity.Match;
//import com.example.moim.match.entity.MatchUser;
//import com.example.moim.match.repository.MatchRepository;
//import com.example.moim.match.repository.MatchUserRepository;
//import com.example.moim.match.service.MatchAggregateService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class MatchAggregateServiceTest {
//
//    @Mock
//    private MatchRepository matchRepository;
//    @Mock
//    private MatchUserRepository matchUserRepository;
//
//    @InjectMocks
//    private MatchAggregateService matchAggregateService;
//
//    private Match match;
//    private Club homeClub;
//    private Club awayClub;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        homeClub = new Club();
//        ReflectionTestUtils.setField(homeClub, "id", 1L);
//        awayClub = new Club();
//        ReflectionTestUtils.setField(awayClub, "id", 2L);
//
//        match = new Match();
//        ReflectionTestUtils.setField(match, "id", 500L);
//        ReflectionTestUtils.setField(match, "homeClub", homeClub);
//        ReflectionTestUtils.setField(match, "awayClub", awayClub);
//        // 초기 점수
//        ReflectionTestUtils.setField(match, "homeScore", 0);
//        ReflectionTestUtils.setField(match, "awayScore", 0);
//        // endTime는 현재시각에서 24시간 이전
//        ReflectionTestUtils.setField(match, "endTime", LocalDateTime.now().minusHours(24));
//
//        when(homeClub.getId()).thenReturn(1L);
//        when(awayClub.getId()).thenReturn(2L);
//    }
//
//    @Test
//    void testAggregateMatchScore() {
//        MatchUser matchUserHome = new MatchUser();
//        ReflectionTestUtils.setField(matchUserHome, "score", 3);
//        ReflectionTestUtils.setField(matchUserHome, "club", homeClub);
//        ReflectionTestUtils.setField(matchUserHome, "user", new com.example.moim.user.entity.User());
//
//        MatchUser matchUserAway = new MatchUser();
//        ReflectionTestUtils.setField(matchUserAway, "score", 2);
//        ReflectionTestUtils.setField(matchUserAway, "club", awayClub);
//        ReflectionTestUtils.setField(matchUserAway, "user", new com.example.moim.user.entity.User());
//
//        MatchUser matchUserNull = new MatchUser();
//        ReflectionTestUtils.setField(matchUserNull, "score", null);
//        ReflectionTestUtils.setField(matchUserNull, "club", homeClub);
//        ReflectionTestUtils.setField(matchUserNull, "user", new com.example.moim.user.entity.User());
//
//        when(matchRepository.findByEndTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
//                .thenReturn(Collections.singletonList(match));
//        when(matchUserRepository.findByMatch(match))
//                .thenReturn(Arrays.asList(matchUserHome, matchUserAway, matchUserNull));
//
//        matchAggregateService.aggregateMatchScore();
//
//        assertEquals(3, ReflectionTestUtils.getField(match, "homeScore"));
//        assertEquals(2, ReflectionTestUtils.getField(match, "awayScore"));
//
//        verify(matchRepository, times(1)).findByEndTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class));
//    }
//
//    @Test
//    void testAggregateMatchScore_NoMatches() {
//        // repository가 빈 리스트 반환 시
//        when(matchRepository.findByEndTimeBetween(any(), any()))
//                .thenReturn(Collections.emptyList());
//
//        // 예외 없이 메서드 호출
//        assertDoesNotThrow(() -> matchAggregateService.aggregateMatchScore());
//
//        // matchUserRepository는 호출되지 않아야 함
//        Mockito.verify(matchUserRepository, Mockito.never()).findByMatch(any());
//    }
//
//    @Test
//    void testAggregateMatchScore_WithValidScores() {
//        // 더미 Match 생성 (spy 사용하여 내부 메서드 호출 확인)
//        Match match = Mockito.spy(new Match());
//        // 필요한 메서드를 스텁: getId(), getHomeClub()
//        Mockito.doReturn(1L).when(match).getId();
//        Mockito.doReturn(homeClub).when(match).getHomeClub();
//
//        // match의 endTime은 repository 리턴 대상이므로 무관함
//        // (테스트에서는 repository에서 이미 원하는 match를 리턴하도록 설정)
//
//        // MatchUser 3개 생성:
//        MatchUser matchUser1 = Mockito.mock(MatchUser.class);
//        when(matchUser1.getScore()).thenReturn(5);
//        when(matchUser1.getClub()).thenReturn(homeClub);
//
//        MatchUser matchUser2 = Mockito.mock(MatchUser.class);
//        when(matchUser2.getScore()).thenReturn(3);
//        when(matchUser2.getClub()).thenReturn(awayClub);
//
//        MatchUser matchUser3 = Mockito.mock(MatchUser.class);
//        when(matchUser3.getScore()).thenReturn(null);
//        when(matchUser3.getClub()).thenReturn(homeClub);
//
//        List<MatchUser> matchUsers = Arrays.asList(matchUser1, matchUser2, matchUser3);
//
//        // repository 모킹
//        when(matchRepository.findByEndTimeBetween(any(), any()))
//                .thenReturn(Collections.singletonList(match));
//        when(matchUserRepository.findByMatch(match))
//                .thenReturn(matchUsers);
//
//        // 메서드 실행
//        matchAggregateService.aggregateMatchScore();
//
//        // aggregateMatchScore 메서드가 match.setMatchScore(5, 3)을 호출했는지 검증
//        verify(match).setMatchScore(5, 3);
//    }
//
//    @Test
//    void testAggregateMatchScore_MultipleMatches() {
//        // 두 개의 매치 생성
//        Match match1 = Mockito.spy(new Match());
//        Mockito.doReturn(101L).when(match1).getId();
//        Mockito.doReturn(homeClub).when(match1).getHomeClub();
//
//        Match match2 = Mockito.spy(new Match());
//        Mockito.doReturn(102L).when(match2).getId();
//        Mockito.doReturn(homeClub).when(match2).getHomeClub();
//
//        // match1: 홈 4, 원정 2
//        MatchUser m1User1 = Mockito.mock(MatchUser.class);
//        when(m1User1.getScore()).thenReturn(4);
//        when(m1User1.getClub()).thenReturn(homeClub);
//
//        MatchUser m1User2 = Mockito.mock(MatchUser.class);
//        when(m1User2.getScore()).thenReturn(2);
//        when(m1User2.getClub()).thenReturn(awayClub);
//
//        // match2: 홈 0, 원정 3 (하나는 null 점수)
//        MatchUser m2User1 = Mockito.mock(MatchUser.class);
//        when(m2User1.getScore()).thenReturn(null);
//        when(m2User1.getClub()).thenReturn(homeClub);
//
//        MatchUser m2User2 = Mockito.mock(MatchUser.class);
//        when(m2User2.getScore()).thenReturn(3);
//        when(m2User2.getClub()).thenReturn(awayClub);
//
//        // 각 매치에 대한 matchUser 리스트 설정
//        when(matchRepository.findByEndTimeBetween(any(), any()))
//                .thenReturn(Arrays.asList(match1, match2));
//        when(matchUserRepository.findByMatch(match1))
//                .thenReturn(Arrays.asList(m1User1, m1User2));
//        when(matchUserRepository.findByMatch(match2))
//                .thenReturn(Arrays.asList(m2User1, m2User2));
//
//        // 메서드 실행
//        matchAggregateService.aggregateMatchScore();
//
//        // 검증: match1의 경우 홈 4, 원정 2, match2는 홈 0, 원정 3
//        verify(match1).setMatchScore(4, 2);
//        verify(match2).setMatchScore(0, 3);
//    }
//}
