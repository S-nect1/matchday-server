package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByClub(Club club);
}
