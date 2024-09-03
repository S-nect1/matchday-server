package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.example.moim.club.entity.ScheduleVote;
import com.example.moim.club.entity.UserClub;
import com.example.moim.match.dto.MatchRecordInput;
import com.example.moim.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MatchUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    private Integer score;

    public static MatchUser createMatchUser(Match match, ScheduleVote scheduleVote) {
        MatchUser matchUser = new MatchUser();
        matchUser.match = match;
        matchUser.user = scheduleVote.getUser();
        matchUser.club = findUserClubInMatch(match, scheduleVote.getUser());

        return matchUser;
    }

    public void recordScore(MatchRecordInput matchRecordInput) {
        this.score = matchRecordInput.getScore();
    }

    private static Club findUserClubInMatch(Match match, User user) {
        for (UserClub userClub : user.getUserClub()) {
            Club myClub = userClub.getClub();
            if (myClub.equals(match.getHomeClub()) || myClub.equals(match.getAwayClub())) {
                return myClub;
            }
        }

        throw new RuntimeException("해당 유저는 매치에 참여한 클럽 소속이 아닙니다.");
    }
}
