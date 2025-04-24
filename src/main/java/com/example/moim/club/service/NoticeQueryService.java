package com.example.moim.club.service;

import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.user.entity.User;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface NoticeQueryService {
    Slice<NoticeOutput> findNotice(User user, Long clubId, Long cursorId);
}
