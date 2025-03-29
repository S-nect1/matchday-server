package com.example.moim.schedule.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.*;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchApplication;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.notification.dto.ScheduleEncourageEvent;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.entity.ScheduleVote;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
import com.example.moim.schedule.repository.ScheduleVoteRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @Mock
    private ScheduleVoteRepository scheduleVoteRepository;
    @Mock
    private MatchApplicationRepository matchApplicationRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    private ScheduleService scheduleService;

    // 필요한 공동 객체
    private ScheduleInput scheduleInput;
    private SignupInput signupInput;
    private ClubInput clubInput;
    private ScheduleUpdateInput scheduleUpdateInput;

    @BeforeEach
    void init() {
        this.scheduleInput = ScheduleInput.builder().clubId(1L).title("title").location("location")
                .startTime(LocalDateTime.of(2024, 12, 13, 12, 30, 0))
                .endTime(LocalDateTime.of(2024, 12, 13, 17, 30, 0))
                .minPeople(10)
                .category("soccer")
                .note("note").build();
        this.signupInput = SignupInput.builder().email("email").password("password")
                .name("name").birthday("birthday").gender(Gender.WOMAN.getKoreanName()).build();
        this.clubInput = ClubInput.builder().title("title").explanation("explanation").introduction("introduction")
                .clubCategory(ClubCategory.SMALL_GROUP.getKoreanName()).organization("organization").gender(Gender.UNISEX.getKoreanName())
                .activityArea(ActivityArea.SEOUL.getKoreanName()).ageRange(AgeRange.TWENTIES.getKoreanName()).sportsType(SportsType.SOCCER.getKoreanName())
                .clubPassword("clubPassword").profileImg(new MockMultipartFile("file", "file".getBytes()))
                .mainUniformColor("mainUniformColor").subUniformColor("subUniformColor").build();
        this.scheduleUpdateInput = ScheduleUpdateInput.builder().clubId(1L).id(1L).title("update title").location("update location")
                .startTime(LocalDateTime.of(2024, 12, 13, 12, 30, 0))
                .endTime(LocalDateTime.of(2024, 12, 13, 17, 30, 0))
                .minPeople(10).category("soccer").note("note").build();
    }

    @Test
    @DisplayName("운영진은 일정을 생성할 수 있다")
    void saveSchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(userClub));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        ScheduleOutput scheduleOutput = scheduleService.saveSchedule(scheduleInput, user);
        //then
        assertThat(scheduleOutput.getTitle()).isEqualTo("title");
        assertThat(scheduleOutput.getMinPeople()).isEqualTo(10);
        assertThat(scheduleOutput.getNote()).isEqualTo("note");
        verify(clubRepository, times(2)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(applicationEventPublisher, times(1)).publishEvent(any(ScheduleSaveEvent.class));
    }

    @Test
    @DisplayName("일반 멤버는 일정을 생성할 때 예외가 발생한다")
    void saveSchedule_wrong_permission() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(userClub));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleService.saveSchedule(scheduleInput, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진은 일정을 변경할 수 있다")
    void updateSchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        //when
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        ScheduleOutput scheduleOutput = scheduleService.updateSchedule(scheduleUpdateInput, user);
        //then
        assertThat(scheduleOutput.getTitle()).isEqualTo("update title");
        assertThat(scheduleOutput.getLocation()).isEqualTo("update location");
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("일반 멤버는 일정을 수정할 때 예외가 발생한다")
    void updateSchedule_wrong_permission() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleService.updateSchedule(scheduleUpdateInput, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(clubRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("한달 일정 조회하기")
    void findSchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(202412).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of(schedule));
        List<ScheduleOutput> result = scheduleService.findSchedule(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("title");
        assertThat(result.get(0).getNote()).isEqualTo("note");
        assertThat(result.get(0).getCategory()).isEqualTo("soccer");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("한달 일정이 없으면 빈 리스트를 반환한다")
    void findSchedule_zero_schedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(202412).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of());
        List<ScheduleOutput> result = scheduleService.findSchedule(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(0);
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("동아리의 하루 일정을 조회할 수 있다")
    void findDaySchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(20241211).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of(schedule));
        List<ScheduleOutput> result = scheduleService.findDaySchedule(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("title");
        assertThat(result.get(0).getNote()).isEqualTo("note");
        assertThat(result.get(0).getCategory()).isEqualTo("soccer");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("스케줄로 그 스케줄의 매치 신청 내역 등 자세한 정보를 볼 수 있다")
    void findScheduleDetail() {
        //given
        Club club = Club.createClub(clubInput, null);
        MatchApplication matchApplication = MatchApplication.applyMatch(new Match(), club);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        schedule.setCreatedDate();
        schedule.setUpdatedDate();
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(matchApplicationRepository.findBySchedule(any(Schedule.class))).thenReturn(List.of(matchApplication));
        ScheduleDetailOutput result = scheduleService.findScheduleDetail(1L);
        //then
        assertThat(result.getMatchApplyClubList().size()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getCategory()).isEqualTo("soccer");
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(matchApplicationRepository, times(1)).findBySchedule(any(Schedule.class));
    }

    @Test
    @DisplayName("멤버는 일정 참가에 대해 재투표를 할 수 있다")
    void voteSchedule_re() {
        //given
        Club club = Club.createClub(clubInput, null);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        ScheduleVoteInput scheduleVoteInput = ScheduleVoteInput.builder().id(1L).attendance("false").build();
        User user = User.createUser(signupInput);
        ScheduleVote scheduleVote = ScheduleVote.createScheduleVote(user, schedule, "true");
        //when
        when(scheduleRepository.findScheduleById(any(Long.class))).thenReturn(schedule);
        when(scheduleVoteRepository.findByScheduleAndUser(any(Schedule.class), any(User.class))).thenReturn(Optional.of(scheduleVote));
        scheduleService.voteSchedule(scheduleVoteInput, user);
        //then
        assertThat(scheduleVote.getAttendance()).isEqualTo("false");
        verify(scheduleRepository, times(1)).findScheduleById(any(Long.class));
        verify(scheduleVoteRepository, times(1)).findByScheduleAndUser(any(Schedule.class), any(User.class));
    }

    @Test
    @DisplayName("멤버는 일정 참가에 대해 투표를 할 수 있다")
    void voteSchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        ScheduleVoteInput scheduleVoteInput = ScheduleVoteInput.builder().id(1L).attendance("false").build();
        User user = User.createUser(signupInput);
        ScheduleVote scheduleVote = ScheduleVote.createScheduleVote(user, schedule, "false");
        //when
        when(scheduleRepository.findScheduleById(any(Long.class))).thenReturn(schedule);
        when(scheduleVoteRepository.findByScheduleAndUser(any(Schedule.class), any(User.class))).thenReturn(Optional.empty());
        when(scheduleVoteRepository.save(any(ScheduleVote.class))).thenReturn(scheduleVote);
        scheduleService.voteSchedule(scheduleVoteInput, user);
        //then
        assertThat(scheduleVote.getAttendance()).isEqualTo("false");
        verify(scheduleRepository, times(1)).findScheduleById(any(Long.class));
        verify(scheduleVoteRepository, times(1)).findByScheduleAndUser(any(Schedule.class), any(User.class));
        verify(scheduleVoteRepository, times(1)).save(any(ScheduleVote.class));
    }

    @Test
    @DisplayName("일정을 삭제할 수 있다")
    void deleteSchedule() {
        //given
        Long id = 1L;
        //when
        scheduleService.deleteSchedule(id);
        //then
        verify(scheduleRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("운영진은 투표를 독려할 수 있다")
    void voteEncourage() {
        //given
        Long id = 1L;
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(scheduleRepository.findScheduleById(any(Long.class))).thenReturn(schedule);
        when(userClubRepository.findUserByClub(club)).thenReturn(List.of(userClub));
        scheduleService.voteEncourage(id);
        //then
        verify(scheduleRepository, times(1)).findScheduleById(any(Long.class));
        verify(userClubRepository, times(1)).findUserByClub(any(Club.class));
        verify(applicationEventPublisher, times(1)).publishEvent(any(ScheduleEncourageEvent.class));
    }

    @Test
    @DisplayName("운영진은 일정 투표를 마감할 수 있다")
    void closeSchedule() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        scheduleService.closeSchedule(1L, user);
        //then
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("일반 회원이 일정 투표를 마감할 때 예외가 발생한다")
    void closeSchedule_wrong_permission() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.createSchedule(club, scheduleInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleService.closeSchedule(1L, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }
}