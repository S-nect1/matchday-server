package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom{
    List<Club> findTop5ByActivityAreaOrderByMemberCount(String activityArea);
}
