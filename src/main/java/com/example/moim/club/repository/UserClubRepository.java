package com.example.moim.club.repository;

import com.example.moim.club.entity.UserClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClubRepository extends JpaRepository<UserClub, Long> {

}
