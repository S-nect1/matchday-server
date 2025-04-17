package com.example.moim.schedule.vote.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.schedule.dto.ScheduleVoteInput;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.vote.entity.ScheduleVote;
import com.example.moim.schedule.vote.repository.ScheduleVoteRepository;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ScheduleVoteServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @Mock
    private ScheduleVoteRepository scheduleVoteRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    private ScheduleVoteServiceImpl scheduleVoteService;

    // 필요한 공동 객체
    private ScheduleInput scheduleInput;
    private SignupInput signupInput;
    private ClubInput clubInput;

    @BeforeEach
    void init() {
        this.scheduleInput = ScheduleInput.builder().clubId(1L).title("title").location("location")
                .startTime(LocalDateTime.of(2024, 12, 13, 12, 30, 0))
                .endTime(LocalDateTime.of(2024, 12, 13, 17, 30, 0))
                .minPeople(10)
                .category("정기 운동")
                .note("note").build();
        this.signupInput = SignupInput.builder().email("email").password("password")
                .name("name").birthday("birthday").gender(Gender.WOMAN.getKoreanName()).build();
        this.clubInput = ClubInput.builder().title("title").explanation("explanation").introduction("introduction")
                .clubCategory(ClubCategory.SMALL_GROUP.getKoreanName()).university("university").gender(Gender.UNISEX.getKoreanName())
                .activityArea(ActivityArea.SEOUL.getKoreanName()).ageRange(AgeRange.TWENTIES.getKoreanName()).sportsType(SportsType.SOCCER.getKoreanName())
                .clubPassword("clubPassword").profileImg(new MockMultipartFile("file", "file".getBytes()))
                .mainUniformColor("mainUniformColor").subUniformColor("subUniformColor").build();
    }


    @Test
    @DisplayName("멤버는 일정 참가에 대해 재투표를 할 수 있다")
    void voteSchedule_re() {
        //given
        Club club = Club.from(clubInput, null);
        Schedule schedule = Schedule.from(club, scheduleInput);
        ScheduleVoteInput scheduleVoteInput = ScheduleVoteInput.builder().id(1L).attendance("불참").build();
        User user = User.createUser(signupInput);
        ScheduleVote scheduleVote = ScheduleVote.createScheduleVote(user, schedule, true);
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(scheduleVoteRepository.findByScheduleAndUser(any(Schedule.class), any(User.class))).thenReturn(Optional.of(scheduleVote));
        scheduleVoteService.voteSchedule(scheduleVoteInput, user);
        //then
        assertThat(scheduleVote.getIsAttendance()).isFalse();
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(scheduleVoteRepository, times(1)).findByScheduleAndUser(any(Schedule.class), any(User.class));
    }

    @Test
    @DisplayName("멤버는 일정 참가에 대해 투표를 할 수 있다")
    void voteSchedule() {
        //given
        Club club = Club.from(clubInput, null);
        Schedule schedule = Schedule.from(club, scheduleInput);
        ScheduleVoteInput scheduleVoteInput = ScheduleVoteInput.builder().id(1L).attendance("불참").build();
        User user = User.createUser(signupInput);
        ScheduleVote scheduleVote = ScheduleVote.createScheduleVote(user, schedule, false);
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(scheduleVoteRepository.findByScheduleAndUser(any(Schedule.class), any(User.class))).thenReturn(Optional.empty());
        when(scheduleVoteRepository.save(any(ScheduleVote.class))).thenReturn(scheduleVote);
        scheduleVoteService.voteSchedule(scheduleVoteInput, user);
        //then
        assertThat(scheduleVote.getIsAttendance()).isFalse();
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(scheduleVoteRepository, times(1)).findByScheduleAndUser(any(Schedule.class), any(User.class));
        verify(scheduleVoteRepository, times(1)).save(any(ScheduleVote.class));
    }

    @Test
    @DisplayName("운영진은 투표를 독려할 수 있다")
    void voteEncourage() {
        //given
        Long id = 1L;
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.from(club, scheduleInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(scheduleRepository.findWithClubById(any(Long.class))).thenReturn(schedule);
        when(userClubRepository.findUserByClub(club)).thenReturn(List.of(userClub));
        scheduleVoteService.voteEncourage(id);
        //then
        verify(scheduleRepository, times(1)).findWithClubById(any(Long.class));
        verify(userClubRepository, times(1)).findUserByClub(any(Club.class));
        verify(applicationEventPublisher, times(1)).publishEvent(any(ScheduleEncourageEvent.class));
    }


    @Test
    @DisplayName("운영진은 일정 투표를 마감할 수 있다")
    void closeSchedule() {
        //given
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.from(club, scheduleInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(scheduleRepository.findWithClubById(any(Long.class))).thenReturn(schedule);
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        scheduleVoteService.closeScheduleVote(1L, user);
        //then
        verify(scheduleRepository, times(1)).findWithClubById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("일반 회원이 일정 투표를 마감할 때 예외가 발생한다")
    void closeSchedule_wrong_permission() {
        //given
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.from(club, scheduleInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(scheduleRepository.findWithClubById(any(Long.class))).thenReturn(schedule);
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleVoteService.closeScheduleVote(1L, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(scheduleRepository, times(1)).findWithClubById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }


}