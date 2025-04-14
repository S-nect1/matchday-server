package com.example.moim.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleUpdateInput {
    private Long clubId;
    private String title;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    @Min(value = 1, message = "참여 인원은 1명 이상이어야 합니다")
    private Integer minPeople;
    private String category;
    private String note;

    @Builder
    public ScheduleUpdateInput(Long clubId, String title, String location, LocalDateTime startTime, LocalDateTime endTime, @NotNull(message = "참여 인원을 입력해주세요.") int minPeople, String category, String note) {
        this.clubId = clubId;
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.minPeople = minPeople;
        this.category = category;
        this.note = note;
    }
}
