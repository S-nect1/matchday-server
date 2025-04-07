package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeOutput;

import java.util.List;

public interface NoticeQueryService {
    List<NoticeOutput> findNotice(Long clubId);
}
