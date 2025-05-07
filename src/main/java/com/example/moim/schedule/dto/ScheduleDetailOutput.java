package com.example.moim.schedule.dto;

import com.example.moim.schedule.comment.dto.CommentOutput;
import com.example.moim.schedule.comment.entity.Comment;
import com.example.moim.schedule.entity.Schedule;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ScheduleDetailOutput {
    private Long id;
    private String title;
    private String deadline;
    private int viewCount;
    private String category;
    private Boolean isClose; // 투표 마감
    // 일정 정보
    private String location; // 주소
    private String period;
    private String note; // 기타 참고 사항
    // 참가 투표
    private int minPeople;
    private int attend;
    private int nonAttend;

    // 댓글 정보
    private List<CommentOutput> comments;
    // 상대 팀 정보
    private Object otherClub;

    // 정기 운동, 기타
    public ScheduleDetailOutput(Schedule schedule, List<CommentOutput> comments) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.deadline = schedule.getCreatedDate().plusDays(5).format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        if (LocalDateTime.now().isBefore(schedule.getCreatedDate().plusDays(5))) {
            this.isClose = schedule.getIsClose();
        } else { //마감일 전이면 마감 상태 응답, 지났으면 무조건 마감한것으로 응답
            this.isClose = true;
        }
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.minPeople = schedule.getMinPeople();
        this.category = schedule.getCategory().getKoreanName();
        this.note = schedule.getNote();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
        this.viewCount = schedule.getViewCount();
        this.comments = comments;
    }

    // 친선 매치, 리그/대회
    public ScheduleDetailOutput(Schedule schedule, List<CommentOutput> comments, Object clubInfo) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.deadline = schedule.getCreatedDate().plusDays(5).format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        if (LocalDateTime.now().isBefore(schedule.getCreatedDate().plusDays(5))) {
            this.isClose = schedule.getIsClose();
        } else { //마감일 전이면 마감 상태 응답, 지났으면 무조건 마감한것으로 응답
            this.isClose = true;
        }
        this.location = schedule.getLocation();
        this.period = schedule.getStartTime().toLocalDate().toString() + " " +
                schedule.getStartTime().toLocalTime().toString() + " ~ " + schedule.getEndTime().toLocalTime().toString();
        this.minPeople = schedule.getMinPeople();
        this.category = schedule.getCategory().getKoreanName();
        this.note = schedule.getNote();
        this.attend = schedule.getAttend();
        this.nonAttend = schedule.getNonAttend();
        this.viewCount = schedule.getViewCount();
        this.comments = comments;
        this.otherClub = clubInfo;
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
