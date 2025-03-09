package com.example.moim.schedule.dto;

import com.example.moim.schedule.entity.Schedule;
import com.example.moim.match.dto.MatchApplyClubOutput;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ScheduleDetailOutput {
    private Long id;
    private String title;
    private String deadline;
    private Boolean isClose;
    private String location;
    private String period;
    private int minPeople;
    private String category;
    private String note;
    private int attend;
    private int nonAttend;
//    List<ScheduleUserOutput> ScheduleUserList;
    List<MatchApplyClubOutput> MatchApplyClubList;

    public ScheduleDetailOutput(Schedule schedule, List<MatchApplyClubOutput> MatchApplyClubOutputList) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.deadline = schedule.getCreatedDate().plusDays(5).format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        if (LocalDateTime.now().isBefore(schedule.getCreatedDate().plusDays(5))) {
            this.isClose = schedule.getIsClose();
        } else {//마감일 전이면 마감 상태 응답, 지났으면 무조건 마감한것으로 응답
            this.isClose = true;
        }
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.minPeople = schedule.getMinPeople();
        this.category = schedule.getCategory();
        this.note = schedule.getNote();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
        this.MatchApplyClubList = MatchApplyClubOutputList;
    }

//    public ScheduleDetailOutput(Schedule schedule, List<ScheduleUserOutput> ScheduleUserOutputList, List<MatchApplyClubOutput> MatchApplyClubOutputList) {
//        this.id = schedule.getId();
//        this.title = schedule.getTitle();
//        this.location = schedule.getLocation();
//        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
//                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
//        this.minPeople = schedule.getMinPeople();
//        this.category = schedule.getCategory();
//        this.note = schedule.getNote();
//        this.attend = schedule.getAttend();
//        this.nonAttend = schedule.getNonAttend();
//        this.ScheduleUserList = ScheduleUserOutputList;
//        this.MatchApplyClubList = MatchApplyClubOutputList;
//    }
}
