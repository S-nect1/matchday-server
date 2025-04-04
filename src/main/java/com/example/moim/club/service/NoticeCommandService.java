package com.example.moim.club.service;

import com.example.moim.club.dto.request.NoticeInput;

public interface NoticeCommandService {
    void saveNotice(NoticeInput noticeInput);
}
