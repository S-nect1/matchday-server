package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Notice;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<Notice> findByCursor(Long cursor, int size, Club club);
}

