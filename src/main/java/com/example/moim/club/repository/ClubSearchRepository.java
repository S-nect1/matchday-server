package com.example.moim.club.repository;

import com.example.moim.club.entity.ClubSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubSearchRepository extends JpaRepository<ClubSearch, Long> {
}
