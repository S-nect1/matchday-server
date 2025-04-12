package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class NoticeQueryServiceImplTest {
    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @InjectMocks
    private NoticeQueryServiceImpl noticeQueryService;

    private ClubInput clubInput;
    private NoticeInput noticeInput;
    private SignupInput signupInput;

    @BeforeEach
    void init() {
        // Club
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

        // Notice
        this.noticeInput = NoticeInput.builder().title("notice title").content("notice content").build();

        // User
        this.signupInput = SignupInput.builder()
                .email("email")
                .phone("phone")
                .birthday("2000-08-28")
                .name("name")
                .password("password")
                .gender(Gender.WOMAN.getKoreanName())
                .build();
    }

    @Test
    @DisplayName("동아리 별 공지들을 조회할 수 있다")
    void findNotice() {
        //given
        Club club = Club.createClub(clubInput, null);
        Notice notice = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);
        notice.setCreatedDate();
        notice.setUpdatedDate();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(noticeRepository.findByCursor(any(Long.class), any(Integer.class), any(Club.class))).thenReturn(List.of(notice));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        Slice<NoticeOutput> result = noticeQueryService.findNotice(user, 1L, 0L);

        //then
        assertThat(result.getSize()).isEqualTo(20);
        assertThat(result.getNumberOfElements()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("notice title");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(noticeRepository, times(1)).findByCursor(any(Long.class), any(Integer.class), any(Club.class));
    }

    @Test
    @DisplayName("동아리에 등록된 공지가 없을 경우 빈 리스트를 반환한다")
    void findNotice_zero_notice() {
        //given
        Club club = Club.createClub(clubInput, null);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(noticeRepository.findByCursor(any(Long.class), any(Integer.class), any(Club.class))).thenReturn(List.of());
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        Slice<NoticeOutput> result = noticeQueryService.findNotice(user, 1L, 0L);
        //then
        assertThat(result.getSize()).isEqualTo(20);
        assertThat(result.getNumberOfElements()).isEqualTo(0);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.hasContent()).isFalse();
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(noticeRepository, times(1)).findByCursor(any(Long.class), any(Integer.class), any(Club.class));
    }

    @Test
    @DisplayName("동아리에 등록된 공지를 조회하면 나중에 저장된 순으로 정렬되어 있다.")
    void findNotice_sort() {
        //given
        Club club = Club.createClub(clubInput, null);
        Notice notice = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        ReflectionTestUtils.setField(notice, "id", 1L);
        LocalDateTime localDateTime = LocalDateTime.now();
        ReflectionTestUtils.setField(notice, "createdDate", localDateTime);
        Notice notice2 = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        ReflectionTestUtils.setField(notice2, "id", 2L);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        ReflectionTestUtils.setField(notice2, "createdDate", localDateTime2);
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createUserClub(user, club);

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(noticeRepository.findByCursor(any(Long.class), any(Integer.class), any(Club.class))).thenReturn(List.of(notice, notice2));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        Slice<NoticeOutput> result = noticeQueryService.findNotice(user, 1L, 0L);

        //then
        assertThat(result.getSize()).isEqualTo(20);
        assertThat(result.getNumberOfElements()).isEqualTo(2);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent().get(0).getCreatedDate()).isEqualTo(LocalDate.from(localDateTime2));
        assertThat(result.getContent().get(1).getCreatedDate()).isEqualTo(LocalDate.from(localDateTime));
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(noticeRepository, times(1)).findByCursor(any(Long.class), any(Integer.class), any(Club.class));
    }
}
