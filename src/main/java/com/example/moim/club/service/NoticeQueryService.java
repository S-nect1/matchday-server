package com.example.moim.club.service;

import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.user.entity.User;

import java.util.List;

public interface NoticeQueryService {
    List<NoticeOutput> findNotice(User user, Long clubId);
}
