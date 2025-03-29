package com.example.moim.match.entity;

import com.example.moim.global.enums.AgeRange;
import com.example.moim.global.enums.Gender;
import com.example.moim.schedule.dto.ScheduleInput;
import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.match.exception.MatchRecordExpireException;
import com.example.moim.global.entity.BaseEntity;
import com.example.moim.match.dto.MatchInput;
import com.example.moim.match.dto.MatchRegInput;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import static com.example.moim.match.entity.MatchHalf.*;
import static com.example.moim.match.entity.MatchStatus.*;

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
    private Integer homeScore;
    private Integer awayScore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String name;
    private String event;
    private String matchSize; //종목 인원수
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private int fee;
    private String bank;
    private String account;
    private int minParticipants; //최소 참가자 수

    // 자동 등록
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private AgeRange ageRange;

    private boolean isBall;
    private String note;

    @Enumerated(EnumType.STRING)
    private MatchHalf matchHalf;

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
        match.bank = matchInput.getBank();
        match.account = matchInput.getAccount();
        match.minParticipants = matchInput.getMinParticipants();

        match.gender = club.getGender();
        match.ageRange = club.getAgeRange();
        match.matchStatus = PENDING;// 초기 상태는 매치 대기
        match.matchHalf = (matchInput.getStartTime().getMonth().getValue() <= 6) ? FIRST_HALF : SECOND_HALF;
        match.homeScore = 0;
        match.awayScore = 0;

        return match;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    //매치 등록
    public void completeMatch(MatchRegInput matchRegInput) {
        this.isBall = matchRegInput.isBall();
        this.note = matchRegInput.getNote();
        this.matchStatus = REGISTERED;
    }

    public void confirmMatch(Club awayClub) {
        this.awayClub = awayClub;
        this.matchStatus = CONFIRMED;
    }

    //매치 실패
    public void failMatch() {
        this.matchStatus = FAILED;
    }

    private static String createMatchName(Club club, MatchInput matchInput) {
        String clubName = club.getTitle();
        String matchType = matchInput.getEvent(); // 종목 (축구, 풋살 등)
        String participants = matchInput.getMatchSize(); // 인원수
        return String.format("%s팀의 %s %s 매치", clubName, participants, matchType);
    }

    public ScheduleInput createScheduleFromMatch() {
        return new ScheduleInput(
                getId(),
                getName(),
                getLocation(),
                getStartTime(),
                getEndTime(),
                getMinParticipants(),
                "친선 매치",
                getNote());
    }


    public Club findOpponentClub(Club club) {
        if (club.getId() == this.getAwayClub().getId()) {
            return this.getHomeClub();
        }

        return this.getAwayClub();
    }

    public void setMatchScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public void timeDuplicationCheck(LocalDateTime startTime, LocalDateTime endTime) {
        if ((startTime.isBefore(this.startTime) && endTime.isBefore(this.startTime)) ||
        (startTime.isAfter(this.endTime) && endTime.isAfter(this.endTime))) {
            throw new MatchRecordExpireException("해당 시간대에 다른 매치 일정이 있습니다");
        }
    }
}