package com.example.moim.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleSearchDayInput {

    @Schema(description = "조회 기준 년월 (YYYYMM 형식, 예: 20240413)", example = "20240413")
    @Pattern(regexp = "^[0-9]{6}$", message = "날짜는 YYYYMMDD 형식이어야 합니다.")
    private final Integer date;
    private final Long clubId;

    @Builder
    public ScheduleSearchDayInput(Integer date, Long clubId) {
        this.date = date;
        this.clubId = clubId;
    }
}
