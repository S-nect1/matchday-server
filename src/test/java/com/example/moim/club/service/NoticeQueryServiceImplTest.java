package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.global.enums.*;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NoticeQueryServiceImplTest {
    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private ClubRepository clubRepository;
    @InjectMocks
    private NoticeQueryServiceImpl noticeQueryService;

    private ClubInput clubInput;
    private NoticeInput noticeInput;

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
        this.noticeInput = NoticeInput.builder().title("notice title").content("notice content").clubId(1L).build();
    }

    @Test
    @DisplayName("동아리 별 공지들을 조회할 수 있다")
    void findNotice() {
        //given
        Club club = Club.createClub(clubInput, null);
        Notice notice = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        notice.setCreatedDate();
        notice.setUpdatedDate();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(noticeRepository.findByClub(any(Club.class))).thenReturn(List.of(notice));
        List<NoticeOutput> result = noticeQueryService.findNotice(1L);
        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo(noticeInput.getTitle());
        assertThat(result.get(0).getContent()).isEqualTo(noticeInput.getContent());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(noticeRepository, times(1)).findByClub(any(Club.class));
    }

    @Test
    @DisplayName("동아리에 등록된 공지가 없을 경우 빈 리스트를 반환한다")
    void findNotice_zero_notice() {
        //given
        Club club = Club.createClub(clubInput, null);

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(noticeRepository.findByClub(any(Club.class))).thenReturn(List.of());
        List<NoticeOutput> result = noticeQueryService.findNotice(1L);
        //then
        assertThat(result.size()).isEqualTo(0);
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(noticeRepository, times(1)).findByClub(any(Club.class));
    }
}
