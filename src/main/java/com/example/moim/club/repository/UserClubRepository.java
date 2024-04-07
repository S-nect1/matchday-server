package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClubRepository extends JpaRepository<UserClub, Long> {

    @Query("select uc from UserClub uc" +
            " join fetch uc.user u" +
            " where uc.club = :club")
    List<UserClub> findAllByClub(@Param("club") Club club);
}
