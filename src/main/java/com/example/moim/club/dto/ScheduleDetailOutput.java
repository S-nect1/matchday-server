package com.example.moim.club.dto;

import com.example.moim.club.entity.Schedule;
import com.example.moim.match.dto.MatchApplyClubOutput;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleDetailOutput {
    private Long id;
    private String title;
    private String location;
    private String period;
    private int minPeople;
    private String category;
    private String note;
    private int attend;
    private int nonAttend;
    List<ScheduleUserOutput> ScheduleUserList;
    List<MatchApplyClubOutput> MatchApplyClubList;

    public ScheduleDetailOutput(Schedule schedule, List<ScheduleUserOutput> ScheduleUserOutputList, List<MatchApplyClubOutput> MatchApplyClubOutputList) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.minPeople = schedule.getMinPeople();
        this.category = schedule.getCategory();
        this.note = schedule.getNote();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
        this.ScheduleUserList = ScheduleUserOutputList;
        this.MatchApplyClubList = MatchApplyClubOutputList;
    }
}
