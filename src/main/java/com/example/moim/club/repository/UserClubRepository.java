package com.example.moim.club.repository;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.UserClub;
import com.example.moim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserClubRepository extends JpaRepository<UserClub, Long> {

    @Query("select uc from UserClub uc" +
            " join fetch uc.user u" +
            " where uc.club = :club")
    List<UserClub> findAllByClub(@Param("club") Club club);

    Optional<UserClub> findByClubAndUser(Club club, User user);

    @Transactional(readOnly = true)
    @Query("select uc from UserClub uc" +
            " join fetch uc.club" +
            " where uc.user = :user")
    List<UserClub> findByUser(@Param("user") User user);

    Boolean existsByUser(User user);

    @Transactional(readOnly = true)
    @Query("select uc from UserClub uc" +
            " join fetch uc.user u" +
            " where uc.club = :club")
    List<UserClub> findUserByClub(@Param("club") Club club);

    @Transactional(readOnly = true)
    @Query("select uc from UserClub uc" +
            " where uc.user.id = :userId" +
            " and uc.category = 'creator'")
    UserClub findByUserIdAndCategory(@Param("userId") Long userId);
}
