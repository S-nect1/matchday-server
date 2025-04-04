package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.ClubSearchCond;
import com.example.moim.club.dto.request.ClubUpdateInput;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSearchOutput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClubQueryServiceImplTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @InjectMocks
    private ClubQueryServiceImpl clubQueryService;

    private ClubInput clubInput;

    @BeforeEach
    void init() {
        String title = "amazing title";
        String explanation = "explanation";
        String introduction = "introduction";
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String university = "university";
        Gender gender = Gender.UNISEX;
        ActivityArea activityArea = ActivityArea.SEOUL;
        AgeRange ageRange = AgeRange.TWENTIES;
        SportsType sportsType = SportsType.SOCCER;
        String clubPassword = "clubPassword";
        MultipartFile profileImg = new MockMultipartFile("profileImg", "profileImg".getBytes());
        String mainUniformColor = "mainUniformColor";
        String subUniformColor = "subUniformColor";

        this.clubInput = ClubInput.builder().title(title).explanation(explanation).introduction(introduction).clubCategory(clubCategory.getKoreanName())
                .university(university).gender(gender.getKoreanName()).activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName())
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();
    }

    @Test
    @DisplayName("동아리 정보로 동아리들을 조회할 수 있다")
    void searchClub() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubSearchCond clubSearchCond = ClubSearchCond.builder().search("searchs").build();

        //when
        when(clubRepository.findBySearchCond(any(ClubSearchCond.class))).thenReturn(List.of(club));
        List<ClubSearchOutput> result = clubQueryService.searchClub(clubSearchCond);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo(club.getTitle());
        assertThat(result.get(0).getExplanation()).isEqualTo(club.getExplanation());
        verify(clubRepository, times(1)).findBySearchCond(any(ClubSearchCond.class));

    }

    @Test
    @DisplayName("동아리에 속한 사용자는 동아리 정보를 조회할 수 있다")
    void findClub() {
        //given
        Club club = Club.createClub(clubInput, null);
        Long id = 1L;
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(UserClub.createLeaderUserClub(user, club)));
        ClubOutput result = clubQueryService.findClub(id, user);

        //then
        assertThat(result.getTitle()).isEqualTo("amazing title");
        assertThat(result.getClubRole()).isEqualTo(ClubRole.STAFF);
        assertThat(result.getMemberCount()).isEqualTo(1);
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }


    @Test
    @DisplayName("동아리에 속하지 않은 사용자는 제한된 동아리 정보를 조회할 수 있다")
    void findClub_not_member() {
        //given
        Club club = Club.createClub(clubInput, null);
        Long id = 1L;
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.empty());
        ClubOutput result = clubQueryService.findClub(id, user);

        //then
        assertThat(result.getTitle()).isEqualTo("amazing title");
        assertThat(result.getClubRole()).isNull();
        assertThat(result.getMemberCount()).isEqualTo(1);
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }
}
