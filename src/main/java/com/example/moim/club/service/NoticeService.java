package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.request.NoticeOutput;
import com.example.moim.club.entity.Notice;
import com.example.moim.club.repository.ClubRepository;
import com.example.moim.club.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;

    public void saveNotice(NoticeInput noticeInput) {
        noticeRepository.save(Notice.createNotice(clubRepository.findById(noticeInput.getClubId()).get(),
                noticeInput.getTitle(), noticeInput.getContent()));
    }

    public List<NoticeOutput> findNotice(Long clubId) {
        return noticeRepository.findByClub(clubRepository.findById(clubId).get()).stream().map(NoticeOutput::new).toList();
    }
}
