package com.example.moim.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleInput {
    private Long clubId;
    @NotBlank(message = "일정 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "일정 장소를 입력해주세요.")
    private String location;
    @Schema(pattern = "yyyy-MM-dd HH:mm", description = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "일정 시작 시간을 입력해주세요.")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "일정 종료 시간을 입력해주세요.")
    private LocalDateTime endTime;
    @Min(value = 1, message = "참여 인원은 1명 이상이어야 합니다")
    @NotNull(message = "최소 참여 인원을 입력해주세요.")
    private int minPeople;//참여인원수
    @NotBlank(message = "일정 카테고리를 입력해 주세요.")
    private String category;
    private String note;

    @Builder
    public ScheduleInput(Long clubId, String title, String location, @NotNull(message = "일정 시작 시간을 입력해주세요.") LocalDateTime startTime, @NotNull(message = "일정 종료 시간을 입력해주세요.") LocalDateTime endTime, @NotNull(message = "최소 참여 인원을 입력해주세요.") int minPeople, String category, String note) {
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
