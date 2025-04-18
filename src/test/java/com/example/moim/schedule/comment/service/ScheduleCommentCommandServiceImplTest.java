package com.example.moim.schedule.comment.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.*;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.comment.repository.CommentRepository;
import com.example.moim.schedule.comment.dto.CommentInput;
import com.example.moim.schedule.dto.ScheduleInput;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleCommentCommandServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserClubRepository userClubRepository;

    @InjectMocks
    private ScheduleCommentCommandServiceImpl scheduleCommentCommandService;

    private ScheduleInput scheduleInput;
    private SignupInput signupInput;
    private ClubInput clubInput;
    private CommentInput commentInput;

    @BeforeEach
    void init() {
        // club, userClub, Schedule 생성
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

        // commentInput 생성
        this.commentInput = CommentInput.builder().contents("일정이 있어 참가 못합니다").build();
    }

    @Test
    @DisplayName("회원은 일정에 댓글을 달 수 있다.")
    void saveComment() {
        // given
        Club club = Club.from(clubInput, "/image");
        User user = User.createUser(signupInput);
        UserClub userClub = UserClub.createLeaderUserClub(user, club);
        Schedule schedule = Schedule.from(club, scheduleInput);
        Comment comment = Comment.createComment(user, schedule, commentInput.getContents());
        LocalDateTime createdDate = LocalDateTime.now();
        ReflectionTestUtils.setField(comment, "createdDate", createdDate);

        // when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.of(userClub));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        scheduleCommentCommandService.saveComment(commentInput, 1L, user);

        // then
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("비회원은 일정에 댓글을 달 수 없다.")
    void saveComment_non_member() {
        // given
        Club club = Club.from(clubInput, "/image");
        User user = User.createUser(signupInput);
        Schedule schedule = Schedule.from(club, scheduleInput);
        Comment comment = Comment.createComment(user, schedule, commentInput.getContents());

        // when
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(userClubRepository.findByClubAndUser(club, user)).thenReturn(Optional.empty());

        // then
        assertThrows(ScheduleControllerAdvice.class, () -> {
            scheduleCommentCommandService.saveComment(commentInput, 1L, user);
        });
        verify(scheduleRepository, times(1)).findById(any(Long.class));
        verify(userClubRepository, times(1)).findByClubAndUser(any(Club.class), any(User.class));
        verify(commentRepository, times(0)).save(any(Comment.class));
    }
}