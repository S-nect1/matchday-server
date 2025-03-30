package com.example.moim.notification.service;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.moim.club.dto.ClubInput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.notification.dto.ClubJoinEvent;
import com.example.moim.notification.entity.Notification;
import com.example.moim.user.dto.SignupInput;
import com.example.moim.user.entity.Gender;
import com.example.moim.user.entity.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class NotificationEventHandlerTest {

    @Mock
    private UserClubRepository userClubRepository;

    @Mock
    private PushNotificationService fcmPushNotificationService;

    @InjectMocks
    private NotificationEventHandler notificationEventHandler;

    @Test
    void handleClubJoinEvent() {
        // given
        SignupInput signupInput = SignupInput.builder()
                .email("test@email.com")
                .password("password")
                .name("name")
                .birthday("2000/05/05")
                .gender(Gender.MAN)
                .phone("01012341234")
                .build();
        User joinedMember = User.createUser(signupInput);

        ClubInput clubInput = ClubInput.builder()
                .title("amazing title")
                .explanation("explanation")
                .introduction("introduction")
                .category("category")
                .university("university")
                .gender("gender")
                .activityArea("activityArea")
                .ageRange("ageRange")
                .mainEvent("mainEvent")
                .clubPassword("clubPassword")
                .profileImg(new MockMultipartFile("profileImg", "profileImg".getBytes()))
                .mainUniformColor("mainUniformColor")
                .subUniformColor("subUniformColor")
                .build();
        Club club = Club.createClub(clubInput, null);

        UserClub userClub = UserClub.createUserClub(joinedMember, club);
        List<UserClub> userClubs = List.of(userClub);

        ClubJoinEvent clubJoinEvent = new ClubJoinEvent(joinedMember, club);

        // when
        when(userClubRepository.findAllByClub(club)).thenReturn(userClubs);
        notificationEventHandler.handleClubJoinEvent(clubJoinEvent);

        // then
        verify(fcmPushNotificationService, times(1)).sendEachNotification(argThat(notifications -> {
            Notification notification = notifications.get(0);
            return notification.getTargetUser().equals(joinedMember) &&
                    notification.getTitle().equals("가입 알림") &&
                    notification.getCategory().equals("모임") &&
                    notification.getContents().equals(joinedMember.getName() + " 님이 " + club.getTitle() + "에 가입했습니다.");
        }));
    }
}