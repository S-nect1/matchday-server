package com.example.moim.club.service;

import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Notice;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private static final int SIZE = 20;

    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;

    public Slice<NoticeOutput> findNotice(User user, Long clubId, Long cursorId) {
        // 가입된 회원인지 확인, 가입되지 않은 회원이면 공지 열람 권한이 없는 것
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));

        List<Notice> notices = noticeRepository.findByCursor(cursorId, SIZE, club);

        boolean hasNext = notices.size() > SIZE;

        if (hasNext) {
            notices.remove(SIZE);
        }

        List<NoticeOutput> outputs = notices.stream()
                .map(NoticeOutput::new)
                .toList();

        return new SliceImpl<>(outputs, PageRequest.of(0, SIZE), hasNext);
    }
}
