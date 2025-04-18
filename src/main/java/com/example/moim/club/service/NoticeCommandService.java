package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeInput;
import com.example.moim.club.dto.response.NoticeOutput;
import com.example.moim.user.entity.User;

public interface NoticeCommandService {
    NoticeOutput saveNotice(User user, NoticeInput noticeInput, Long clubId);
}
