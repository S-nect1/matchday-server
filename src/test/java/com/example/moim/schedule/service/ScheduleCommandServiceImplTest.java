package com.example.moim.schedule.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.notification.dto.ScheduleSaveEvent;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import com.example.moim.schedule.repository.ScheduleRepository;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleCommandServiceImplTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    private ScheduleCommandServiceImpl scheduleCommandService;

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
                .category("정기 운동")
                .note("note").build();
        this.signupInput = SignupInput.builder().email("email").password("password")
                .name("name").birthday("birthday").gender(Gender.WOMAN.getKoreanName()).build();
        this.clubInput = ClubInput.builder().title("title").explanation("explanation").introduction("introduction")
                .clubCategory(ClubCategory.SMALL_GROUP.getKoreanName()).university("university").gender(Gender.UNISEX.getKoreanName())
                .activityArea(ActivityArea.SEOUL.getKoreanName()).ageRange(AgeRange.TWENTIES.getKoreanName()).sportsType(SportsType.SOCCER.getKoreanName())
                .clubPassword("clubPassword").profileImg(new MockMultipartFile("file", "file".getBytes()))
                .mainUniformColor("mainUniformColor").subUniformColor("subUniformColor").build();
        this.scheduleUpdateInput = ScheduleUpdateInput.builder().clubId(1L).title("update title").location("update location")
                .startTime(LocalDateTime.of(2024, 12, 13, 12, 30, 0))
                .endTime(LocalDateTime.of(2024, 12, 13, 17, 30, 0))
                .minPeople(10).category("친선 매치").note("note").build();
    }

    @Test
    @DisplayName("운영진은 일정을 생성할 수 있다")
    void saveSchedule() {
        //given
        Club club = Club.from(clubInput, null);
        Schedule schedule = Schedule.from(club, scheduleInput);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(userClub));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        ScheduleOutput scheduleOutput = scheduleCommandService.saveSchedule(scheduleInput, user);
        //then
        assertThat(scheduleOutput.getTitle()).isEqualTo("title");
        assertThat(scheduleOutput.getMinPeople()).isEqualTo(10);
        assertThat(scheduleOutput.getNote()).isEqualTo("note");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(applicationEventPublisher, times(1)).publishEvent(any(ScheduleSaveEvent.class));
    }

    @Test
    @DisplayName("일반 멤버는 일정을 생성할 때 예외가 발생한다")
    void saveSchedule_wrong_permission() {
        //given
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(userClub));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleCommandService.saveSchedule(scheduleInput, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진은 일정을 변경할 수 있다")
    void updateSchedule() {
        //given
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        Schedule schedule = Schedule.from(club, scheduleInput);
        //when
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        ScheduleOutput scheduleOutput = scheduleCommandService.updateSchedule(scheduleUpdateInput, 1L, user);
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
        Club club = Club.from(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        //when
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        //then
        Exception exception = assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleCommandService.updateSchedule(scheduleUpdateInput, 1L, user);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(clubRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("일정을 삭제할 수 있다")
    void deleteSchedule() {
        //given
        Long id = 1L;
        //when
        scheduleCommandService.deleteSchedule(id);
        //then
        verify(scheduleRepository, times(1)).deleteById(any(Long.class));
    }
}