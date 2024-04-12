package com.example.moim.club.repository;

import com.example.moim.club.entity.Award;
import com.example.moim.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long>, AwardRepositoryCustom {
    List<Award> findByClub(Club club);
}
