package com.example.moim.statistic.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.statistic.dto.StatisticDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "전적 api", description = "전적 관련 api")
public interface StatisticControllerDocs {
    @Operation(summary = "전적 조회")
    BaseResponse<StatisticDTO.StatisticOutPut> getStatistic(@PathVariable Long clubId,
                                                            @RequestBody StatisticDTO.StatisticInput request);
}
