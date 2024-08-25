package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.match.dto.MatchInput;
import com.example.moim.match.dto.MatchRegInput;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.example.moim.match.entity.Status.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_schedule")
    private Schedule schedule;

    private String name;
    private String event;
    private String matchSize; //종목 인원수
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private int fee;
    private String account;
    private int minParticipants; //최소 참가자 수

    // 자동 등록
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String ageRange;

    private boolean isBall;
    private String note;

//    @JoinColumn(name = "prev_match_id")
//    private Match relateMatch; //기존 경기 정보 저장

    //매치 생성
    public static Match createMatch(Club club, MatchInput matchInput) {
        Match match = new Match();

        match.homeClub = club;
        match.name = createMatchName(club, matchInput);
        match.event = matchInput.getEvent();
        match.matchSize = matchInput.getMatchSize();
        match.startTime = matchInput.getStartTime();
        match.endTime = matchInput.getEndTime();
        match.location = matchInput.getLocation();
        match.fee = matchInput.getFee();
        match.account = matchInput.getAccount();
        match.minParticipants = matchInput.getMinParticipants();

        match.gender = Gender.valueOf(match.getHomeClub().getGender());
        match.ageRange = match.getHomeClub().getAgeRange();
        match.status = PENDING;// 초기 상태는 매치 대기
//        if (matchInput.getRelateMatch() != null) {
//            match.relateMatch = matchInput.getRelateMatch();
//        }

        return match;
    }

    public void applyMatch(Club club) {
        this.awayClub = club;
        this.status = PENDING_APP;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    //매치 등록
    public void completeMatch(MatchRegInput matchRegInput) {
        this.isBall = matchRegInput.isBall();
        this.note = matchRegInput.getNote();
        this.status = REGISTERED;
    }

    //매치 실패
    public void failMatch() {
        this.status = FAILED;
    }

    private static String createMatchName(Club club, MatchInput matchInput) {
        String clubName = club.getTitle();
        String matchType = matchInput.getEvent(); // 종목 (축구, 풋살 등)
        String participants = matchInput.getMatchSize(); // 인원수
        return String.format("%s팀의 %s %s 매치", clubName, participants, matchType);
    }
}