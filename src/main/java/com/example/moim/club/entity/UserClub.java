package com.example.moim.club.entity;

import com.example.moim.global.entity.BaseEntity;
import com.example.moim.global.enums.ClubRole;
import com.example.moim.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class UserClub extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
    @Enumerated(value = EnumType.STRING)
    private ClubRole clubRole;      // notnull
    private LocalDate joinDate;
    private Integer scheduleCount;
    private Integer matchCount;

    private int score;

    public static UserClub createLeaderUserClub(User user, Club club) {
        UserClub userClub = new UserClub();
        userClub.user = user;
        userClub.club = club;
        userClub.clubRole = ClubRole.STAFF;
        userClub.joinDate = LocalDate.now();
        return userClub;
    }

    public static UserClub createUserClub(User user, Club club) {
        UserClub userClub = new UserClub();
        userClub.user = user;
        userClub.club = club;
        userClub.clubRole = ClubRole.MEMBER;
        userClub.joinDate = LocalDate.now();
        //카테고리?
        return userClub;
    }

//    public void changeUserClub(String position, ClubRole clubRole) {
//        if (position != null) {
//            this.clubRole = position;
//        }
//        if (responsibility != null) {
//            this.responsibility = responsibility;
//        }
//    }

    public void changeUserClub(ClubRole clubRole) {
        this.clubRole = clubRole;
    }

    public void updateScore(int score) {
        this.score += score;
    }
}
