package com.example.moim.club.service;

import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;
    private final UserClubRepository userClubRepository;

    public List<NoticeOutput> findNotice(User user, Long clubId) {
        // 가입된 회원인지 확인
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND));
        UserClub userClub = userClubRepository.findByClubAndUser(club, user).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_USER_NOT_FOUND));

        // 공지 조회 기준에 따라..(음 유저별로 여러 클럽들의 공지들을 다 조회하는 건가?)
        return noticeRepository.findByClub(club).stream().map(NoticeOutput::new).toList();
    }
}
