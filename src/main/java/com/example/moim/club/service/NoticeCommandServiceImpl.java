package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeInput;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService {
    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;

    public NoticeOutput saveNotice(User user, NoticeInput noticeInput, Long clubId) {
        log.debug("saveNotice 진입");
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));

        if (!userClub.getClubRole().equals(ClubRole.STAFF)) { // 공지 등록 권한 확인
            log.debug("권한 오류");
            throw new ClubControllerAdvice(ResponseCode.CLUB_PERMISSION_DENIED);
        }

        Notice notice = noticeRepository.save(Notice.createNotice(club, noticeInput.getTitle(), noticeInput.getContent()));

        /**
         * TODO: 알림 보내는 부분 구현해야함
         */

        return new NoticeOutput(notice);
    }
}
