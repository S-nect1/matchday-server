package com.example.moim.club.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleUpdateInput {
    private Long clubId;
    private Long id;
    @NotBlank(message = "일정 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "일정 장소를 입력해주세요.")
    private String location;
    @Schema(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "일정 시작 시간을 입력해주세요.")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @NotNull(message = "일정 종료 시간을 입력해주세요.")
    private LocalDateTime endTime;
    @Min(value = 1, message = "참여 인원은 1명 이상이어야 합니다")
    @NotNull(message = "참여 인원을 입력해주세요.")
    private int personnel;//참여인원수
    @NotBlank(message = "일정 카테고리를 입력해 주세요.")
    private String category;
    private String note;
}
