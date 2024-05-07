package com.example.moim.club.entity;

import com.example.moim.global.entity.BaseEntity;
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
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String position;
    private LocalDate joinDate;
    private String category;

    public static UserClub createLeaderUserClub(User user, Club club) {
        UserClub userClub = new UserClub();
        userClub.user = user;
        userClub.club = club;
        userClub.position = "leader";
        userClub.joinDate = LocalDate.now();
        userClub.category = "creator";
        return userClub;
    }

    public static UserClub createUserClub(User user, Club club) {
        UserClub userClub = new UserClub();
        userClub.user = user;
        userClub.club = club;
        userClub.position = "member";
        userClub.joinDate = LocalDate.now();
        userClub.category = "newmember";
        //카테고리?
        return userClub;
    }

    public void changeUserClub(String position, String category) {
        if (position != null) {
            this.position = position;
        }
        if (category != null) {
            this.category = category;
        }
    }
}
