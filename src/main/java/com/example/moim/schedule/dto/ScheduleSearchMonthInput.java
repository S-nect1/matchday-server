package com.example.moim.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleSearchMonthInput {

    @Schema(description = "조회 기준 년월 (YYYYMM 형식, 예: 202404)", example = "202404")
    @Pattern(regexp = "^[0-9]{6}$", message = "날짜는 YYYYMM 형식이어야 합니다.")
    private final Integer date;
    private final Long clubId;
    private final String search;
    private final String category;

    @Builder
    public ScheduleSearchMonthInput(Integer date, Long clubId, String search, String category) {
        this.date = date;
        this.clubId = clubId;
        this.search = search;
        this.category = category;
    }
}
