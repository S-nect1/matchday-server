package com.example.moim.club.service;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.ClubOutput;
import com.example.moim.club.dto.response.ClubSaveOutput;
import com.example.moim.club.dto.response.ClubUpdateOutput;
import com.example.moim.club.dto.response.UserClubOutput;
import com.example.moim.club.entity.*;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.ClubSearchRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.FileStore;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubCommandServiceImplTest {

    @Mock
    private ClubRepository clubRepository;
    @Mock
    private UserClubRepository userClubRepository;
    @Mock
    private FileStore fileStore;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClubSearchRepository clubSearchRepository;
    @InjectMocks
    private ClubCommandServiceImpl clubCommandService;

    private ClubInput clubInput;
    private ClubUpdateInput clubUpdateInput;

    @BeforeEach
    void init() {
        String title = "amazing title";
        String explanation = "explanation";
        String introduction = "introduction";
        ClubCategory clubCategory = ClubCategory.SMALL_GROUP;
        String organization = "organization";
        Gender gender = Gender.UNISEX;
        ActivityArea activityArea = ActivityArea.SEOUL;
        AgeRange ageRange = AgeRange.TWENTIES;
        SportsType sportsType = SportsType.SOCCER;
        String clubPassword = "clubPassword";
        MultipartFile profileImg = new MockMultipartFile("profileImg", "profileImg".getBytes());
        String mainUniformColor = "mainUniformColor";
        String subUniformColor = "subUniformColor";

        this.clubInput = ClubInput.builder().title(title).explanation(explanation).introduction(introduction).clubCategory(clubCategory.getKoreanName())
                .university(organization).gender(gender.getKoreanName()).activityArea(activityArea.getKoreanName()).ageRange(ageRange.getKoreanName()).sportsType(sportsType.getKoreanName())
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();

        String updateTitle = "update title";
        String updateExplanation = "update explanation";

        this.clubUpdateInput = ClubUpdateInput.builder()
                .title(updateTitle).explanation(updateExplanation)
                .clubPassword("clubPassword").build();

    }

    @Test
    @DisplayName("새로운 동아리를 저장할 수 있다")
    void saveClub() throws IOException {
        //given
        Club club = Club.createClub(clubInput, null);
        UserClub userClub = UserClub.createLeaderUserClub(new User(), club);
        ClubSearch clubSearch = ClubSearch.builder().build();
        club.updateClubSearch(clubSearch);
        //when
        when(clubRepository.save(any(Club.class))).thenReturn(club);
        when(userClubRepository.save(any(UserClub.class))).thenReturn(userClub);
        when(fileStore.storeFile(any())).thenReturn(null);
        when(clubSearchRepository.save(any(ClubSearch.class))).thenReturn(clubSearch);
        ClubSaveOutput clubOutput = clubCommandService.saveClub(new User(), clubInput);

        //then
        assertThat(clubOutput).isNotNull();
        verify(clubRepository, times(1)).save(any(Club.class));
        verify(userClubRepository, times(1)).save(any(UserClub.class));
        verify(fileStore, times(1)).storeFile(any());
        verify(clubSearchRepository, times(1)).save(any(ClubSearch.class));
    }

    @Test
    @DisplayName("동아리 정보를 업데이트 할 수 있다")
    void updateClub() throws IOException {
        //given
        Club club = Club.createClub(clubInput, null);
        UserClub userClub = UserClub.createLeaderUserClub(new User(), club);
        ClubSearch clubSearch = ClubSearch.builder().build();
        club.updateClubSearch(clubSearch);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        when(fileStore.storeFile(null)).thenReturn(null);
        ClubUpdateOutput clubOutput = clubCommandService.updateClub(new User(), clubUpdateInput, 1L);
        //then
        assertThat(clubOutput).isNotNull();
        assertThat(clubOutput.getTitle()).isEqualTo("update title");
        assertThat(clubOutput.getExplanation()).isEqualTo("update explanation");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(fileStore, times(1)).storeFile(any());
    }

    @Test
    @DisplayName("동아리 정보를 업데이트 할 때 비밀번호가 틀리면 예외가 발생한다")
    void updateClub_exception_wrong_password() {
        //given
        Club club = Club.createClub(clubInput, null);
        UserClub userClub = UserClub.createLeaderUserClub(new User(), club);
        //when
        //then
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.updateClub(new User(), ClubUpdateInput.builder().clubPassword("wrong!").build(), 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PASSWORD_INCORRECT.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("동아리 정보를 업데이트 할 때 권한이 없으면 예외가 발생한다")
    void updateClub_exception_wrong_auth() {
        //given
        Club club = Club.createClub(clubInput, null);
        UserClub userClub = UserClub.createUserClub(new User(), club);
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(userClub));
        //then
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.updateClub(new User(), clubUpdateInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("사용자는 동아리에 가입할 수 있다")
    void saveClubUser() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubUserSaveInput clubUserSaveInput = ClubUserSaveInput.builder().clubPassword("clubPassword").build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.save(any(UserClub.class))).thenReturn(UserClub.createUserClub(new User(), club));
        UserClubOutput result = clubCommandService.saveClubUser(new User(), clubUserSaveInput, 1L);

        //then
        assertThat(result.getClubRole()).isEqualTo(ClubRole.MEMBER.name());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).save(any(UserClub.class));
        verify(eventPublisher, times(1)).publishEvent(any(ClubJoinEvent.class));
    }

    @Test
    @DisplayName("사용자가 동아리에 가입할 때 틀린 비밀번호를 입력하면 예외가 발생한다")
    void saveClubUser_exception_wrong_password() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubUserSaveInput clubUserSaveInput = ClubUserSaveInput.builder().clubPassword("wrong!").build();
        //when
        //then
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.saveClubUser(new User(), clubUserSaveInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PASSWORD_INCORRECT.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(0)).save(any(UserClub.class));
        verify(eventPublisher, times(0)).publishEvent(any(ClubJoinEvent.class));
    }

    @Test
    @DisplayName("운영진은 동아리에 관련된 사용자 정보를 변경할 수 있다")
    void updateClubUser() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubUserUpdateInput clubUserUpdateInput = ClubUserUpdateInput.builder()
                .userId(1L).clubRole(ClubRole.ADMIN.name()).build();

        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class)))
                .thenReturn(Optional.of(UserClub.createLeaderUserClub(new User(), club)))
                .thenReturn(Optional.of(UserClub.createUserClub(new User(), club)));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));
        UserClubOutput result = clubCommandService.updateClubUser(new User(), clubUserUpdateInput, 1L);

        //then
        assertThat(result.getClubRole()).isEqualTo(ClubRole.ADMIN.name());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(2)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진이 아니면 동아리에 속한 사용자의 정보를 변경하려 할 때 예외가 발생한다")
    void updateClubUser_exception_wrong_permission() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubUserUpdateInput clubUserUpdateInput = ClubUserUpdateInput.builder()
                .userId(1L).clubRole(ClubRole.ADMIN.name()).build();

        //when
        //then
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class)))
                .thenReturn(Optional.of(UserClub.createUserClub(new User(), club)));

        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.updateClubUser(new User(), clubUserUpdateInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진은 동아리 인증 비밀번호를 바꿀 수 있다")
    void clubPasswordUpdate() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubPswdUpdateInput clubPswdUpdateInput = ClubPswdUpdateInput.builder().id(1L).oldPassword("clubPassword").newPassword("newPassword").rePassword("newPassword").build();
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(UserClub.createLeaderUserClub(user, club)));
        clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput, 1L);
        //then
        assertThat(club.getClubPassword()).isEqualTo("newPassword");
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("일반 멤버가 인증 비밀번호를 바꾸려고 하면 예외가 발생한다")
    void clubPasswordUpdate_exception_wrong_permission() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubPswdUpdateInput clubPswdUpdateInput = ClubPswdUpdateInput.builder().oldPassword("clubPassword").newPassword("newPassword").rePassword("newPassword").build();
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(UserClub.createUserClub(user, club)));

        //then
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PERMISSION_DENIED.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진이 동아리 비밀번호를 잘못 입력하면 예외가 발생한다")
    void clubPasswordUpdate_exception_wrong_password() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubPswdUpdateInput clubPswdUpdateInput = ClubPswdUpdateInput.builder().oldPassword("wrong!").newPassword("newPassword").rePassword("newPassword").build();
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(UserClub.createLeaderUserClub(user, club)));

        //then
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_PASSWORD_INCORRECT.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }

    @Test
    @DisplayName("운영진이 새로운 비밀번호와 확인 비밀번호를 다르게 입력하면 예외가 발생한다")
    void clubPasswordUpdate_exception_wrong_check_password() {
        //given
        Club club = Club.createClub(clubInput, null);
        ClubPswdUpdateInput clubPswdUpdateInput = ClubPswdUpdateInput.builder().oldPassword("clubPassword").newPassword("newPassword").rePassword("wrong!").build();
        User user = new User();
        //when
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        when(userClubRepository.findByClubAndUser(any(Club.class), any(User.class))).thenReturn(Optional.of(UserClub.createLeaderUserClub(user, club)));

        //then
        Exception exception = assertThrows(ClubControllerAdvice.class, () -> {
            clubCommandService.clubPasswordUpdate(user, clubPswdUpdateInput, 1L);
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.CLUB_CHECK_PASSWORD_INCORRECT.getMessage());
        verify(clubRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
    }
}