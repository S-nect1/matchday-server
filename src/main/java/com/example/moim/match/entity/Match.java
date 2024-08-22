package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.match.dto.MatchInput;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "matches")
public class Match extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_club_id")
    private Club homeClub;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id")
    private Club awayClub;
    private String name;
    private String event;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private int participants;
    private int fee;
    private String account;
    @Enumerated(EnumType.STRING)
    private Gender gender;
//    private String color;
//    private boolean hasBall;
    private String note;
//    private String photo; //url이나 파일경로를 통해 저장

//    @ManyToOne
//    @JoinColumn(name = "prev_match_id")
//    private Match relateMatch; //기존 경기 정보 저장

    public static Match createMatch(Club club, MatchInput matchInput) {
        Match match = new Match();
        
        match.homeClub = club;
        match.name = matchInput.getName();
        match.startTime = matchInput.getStartTime();
        match.endTime = matchInput.getEndTime();
        match.location = matchInput.getLocation();
        match.participants = matchInput.getCount();
        match.fee = matchInput.getFee();
        match.account = matchInput.getAccount();
        match.gender = matchInput.getGender();
//        match.color = matchInput.getColor();
//        match.hasBall = matchInput.isHasBall();
        if (matchInput.getNote() != null) {
            match.note = matchInput.getNote();
        }
//        if (matchInput.getPhoto() != null) {
//            match.photo = matchInput.getPhoto();
//        }
//        if (matchInput.getRelateMatch() != null) {
//            match.relateMatch = matchInput.getRelateMatch();
//        }

        return match;
    }
}