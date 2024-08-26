package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MatchApplication {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "match_schedule")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private MatchAppStatus status;

    public static MatchApplication applyMatch(Match match, Club club) {
        MatchApplication matchApplication = new MatchApplication();

        matchApplication.match = match;
        matchApplication.club = club;
        matchApplication.status = MatchAppStatus.PENDING_APP;

        return matchApplication;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
