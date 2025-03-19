package com.example.moim.club.service;

import com.example.moim.club.dto.request.ClubInput;
import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Notice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
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
class NoticeCommandServiceImplTest {

    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private ClubRepository clubRepository;
    @InjectMocks
    private NoticeCommandServiceImpl noticeCommandServiceImpl;

    private ClubInput clubInput;
    private NoticeInput noticeInput;
    @BeforeEach
    void init() {
        // Club
        String title = "amazing title";
        String explanation = "explanation";
        String introduction = "introduction";
        String category = "category";
        String university = "university";
        String gender = "gender";
        String activityArea = "activityArea";
        String ageRange = "ageRange";
        String mainEvent = "mainEvent";
        String clubPassword = "clubPassword";
        MultipartFile profileImg = new MockMultipartFile("profileImg", "profileImg".getBytes());
        String mainUniformColor = "mainUniformColor";
        String subUniformColor = "subUniformColor";

        this.clubInput = ClubInput.builder().title(title).explanation(explanation).introduction(introduction).category(category)
                .university(university).gender(gender).activityArea(activityArea).ageRange(ageRange).mainEvent(mainEvent)
                .clubPassword(clubPassword).profileImg(profileImg).mainUniformColor(mainUniformColor).subUniformColor(subUniformColor).build();

        // Notice
        this.noticeInput = NoticeInput.builder().title("notice title").content("notice content").clubId(1L).build();
    }
    @Test
    @DisplayName("공지를 저장할 수 있다")
    void saveNotice() {
        //given
        Club club = Club.createClub(clubInput, null);
        Notice notice = Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent());
        //when
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);
        when(clubRepository.findById(any(Long.class))).thenReturn(Optional.of(club));
        noticeCommandServiceImpl.saveNotice(noticeInput);
        //then
        verify(noticeRepository, times(1)).save(any(Notice.class));
        verify(clubRepository, times(1)).findById(any(Long.class));
    }

}