package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.entity.Notice;
import com.example.moim.club.exception.advice.ClubControllerAdvice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import com.example.moim.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService {
    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;

    /**
     * transaction 필요하지 않음. save 작업 하나만 이루어지고, 나머지는 다 조회 연산이므로
     * save 함수 내부에 이미 transaction 적용되어 있음
     * @param noticeInput
     */
    public void saveNotice(NoticeInput noticeInput) {
        noticeRepository.save(Notice.createNotice(clubRepository.findById(noticeInput.getClubId()).orElseThrow(() -> new ClubControllerAdvice(ResponseCode.CLUB_NOT_FOUND)),
                noticeInput.getTitle(), noticeInput.getContent()));
    }
}
