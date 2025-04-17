package com.example.moim.schedule.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.match.entity.Match;
import com.example.moim.match.entity.MatchApplication;
import com.example.moim.match.repository.MatchApplicationRepository;
import com.example.moim.schedule.dto.*;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.schedule.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ScheduleQueryServiceImplTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private MatchApplicationRepository matchApplicationRepository;
    @InjectMocks
    private ScheduleQueryServiceImpl scheduleQueryService;

    // 필요한 공동 객체
    private ScheduleInput scheduleInput;
    private ClubInput clubInput;

    @BeforeEach
    void init() {
        this.scheduleInput = ScheduleInput.builder().clubId(1L).title("title").location("location")
                .startTime(LocalDateTime.of(2024, 12, 13, 12, 30, 0))
                .endTime(LocalDateTime.of(2024, 12, 13, 17, 30, 0))
                .minPeople(10)
                .category("정기 운동")
                .note("note").build();
        this.clubInput = ClubInput.builder().title("title").explanation("explanation").introduction("introduction")
                .clubCategory(ClubCategory.SMALL_GROUP.getKoreanName()).university("university").gender(Gender.UNISEX.getKoreanName())
                .activityArea(ActivityArea.SEOUL.getKoreanName()).ageRange(AgeRange.TWENTIES.getKoreanName()).sportsType(SportsType.SOCCER.getKoreanName())
                .clubPassword("clubPassword").profileImg(new MockMultipartFile("file", "file".getBytes()))
                .mainUniformColor("mainUniformColor").subUniformColor("subUniformColor").build();
    }

    @Test
    @DisplayName("한달 일정 조회하기")
    void findSchedule() {
        //given
        Club club = Club.from(clubInput, null);
        Schedule schedule = Schedule.from(club, scheduleInput);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(202412).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of(schedule));
        List<ScheduleOutput> result = scheduleQueryService.findMonthlySchedulesWithFilter(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("title");
        assertThat(result.get(0).getNote()).isEqualTo("note");
        assertThat(result.get(0).getCategory()).isEqualTo("정기 운동");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("한달 일정이 없으면 빈 리스트를 반환한다")
    void findSchedule_zero_schedule() {
        //given
        Club club = Club.from(clubInput, null);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(202412).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of());
        List<ScheduleOutput> result = scheduleQueryService.findMonthlySchedulesWithFilter(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(0);
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("동아리의 하루 일정을 조회할 수 있다")
    void findDaySchedule() {
        //given
        Club club = Club.from(clubInput, null);
        Schedule schedule = Schedule.from(club, scheduleInput);
        ScheduleSearchInput scheduleSearchInput = ScheduleSearchInput.builder().date(20241211).clubId(1L).search("title").category("soccer").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(scheduleRepository.findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class))).thenReturn(List.of(schedule));
        List<ScheduleOutput> result = scheduleQueryService.findScheduleByDay(scheduleSearchInput);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("title");
        assertThat(result.get(0).getNote()).isEqualTo("note");
        assertThat(result.get(0).getCategory()).isEqualTo("정기 운동");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(scheduleRepository, times(1)).findByClubAndTime(any(Club.class), any(LocalDateTime.class), any(LocalDateTime.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("스케줄로 그 스케줄의 매치 신청 내역 등 자세한 정보를 볼 수 있다")
    void findScheduleDetail() {
        //given
        Club club = Club.from(clubInput, null);
        MatchApplication matchApplication = MatchApplication.applyMatch(new Match(), club);
        Schedule schedule = Schedule.from(club, scheduleInput);
        schedule.setCreatedDate();
        schedule.setUpdatedDate();
        //when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(matchApplicationRepository.findBySchedule(any(Schedule.class))).thenReturn(List.of(matchApplication));
        ScheduleDetailOutput result = scheduleQueryService.findScheduleDetail(1L);
        //then
        assertThat(result.getMatchApplyClubList().size()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getCategory()).isEqualTo("정기 운동");
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(matchApplicationRepository, times(1)).findBySchedule(any(Schedule.class));
    }
}
