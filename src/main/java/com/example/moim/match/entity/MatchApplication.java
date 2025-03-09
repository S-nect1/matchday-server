package com.example.moim.match.entity;

import com.example.moim.club.entity.Club;
import com.example.moim.schedule.entity.Schedule;
import com.example.moim.match.dto.MatchApplyInput;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MatchApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void completeApplication(MatchApplyInput matchApplyInput) {
        this.isBall = matchApplyInput.isBall();
        this.note = matchApplyInput.getNote();
        this.status = MatchAppStatus.APP_COMPLETED;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void confirmMatch() {
        this.status = MatchAppStatus.CONFIRMED;
    }

    public void rejectMatch() {
        this.status = MatchAppStatus.REJECTED;
    }

    public void failApply() {
        this.status = MatchAppStatus.REJECTED;
    }
}
