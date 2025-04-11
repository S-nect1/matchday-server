package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.entity.*;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeCommandServiceImplTest {

    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @InjectMocks
    private NoticeCommandServiceImpl noticeCommandServiceImpl;

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
        this.noticeInput = NoticeInput.builder().title("notice title").content("notice content").clubId(1L).build();

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
    @DisplayName("공지를 저장할 수 있다")
    void saveNotice() {
        //given
        Club club = Club.createClub(clubInput, null);
        Notice notice = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        // notice - createAt 강제로 주입하기
        ReflectionTestUtils.setField(notice, "createdDate", LocalDateTime.now());
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        //when
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        noticeCommandServiceImpl.saveNotice(user, noticeInput);
        //then
        verify(noticeRepository, times(1)).save(any(Notice.class));
        verify(clubRepository, times(1)).findById(any(Long.class));
    }

}