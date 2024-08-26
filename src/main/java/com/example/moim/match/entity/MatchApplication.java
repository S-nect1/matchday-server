package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.club.entity.Schedule;
import com.example.moim.match.dto.MatchRegInput;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MatchApplication {

    @Id @GeneratedValue
    private Long id;

    private boolean isBall;
    private String note;

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

    public void completeApplication(MatchRegInput matchRegInput) {
        this.isBall = matchRegInput.isBall();
        this.note = matchRegInput.getNote();
        this.status = MatchAppStatus.APP_COMPLETED;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void failApply() {
        this.status = MatchAppStatus.REJECTED;
    }
}
