package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;

    public List<NoticeOutput> findNotice(Long clubId) {
        return noticeRepository.findByClub(clubRepository.findById(clubId).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND))).stream().map(NoticeOutput::new).toList();
    }
}
