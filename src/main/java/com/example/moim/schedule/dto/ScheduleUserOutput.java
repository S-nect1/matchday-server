package com.example.moim.schedule.dto;

import com.example.moim.schedule.vote.entity.ScheduleVote;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class ScheduleUserOutput {
    private String name;
    private String img;
    private String attendance;

    public ScheduleUserOutput(ScheduleVote scheduleVote) {
        this.name = scheduleVote.getUser().getName();
        if (scheduleVote.getUser().getImgPath() != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(scheduleVote.getUser().getImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (scheduleVote.getIsAttendance() != null) {
            this.attendance = scheduleVote.getIsAttendance()? "참가" : "불참";
        } else {
            this.attendance = "notVote";
        }
    }
}
