package com.example.moim.statistic.controller;

import com.example.moim.global.exception.BaseResponse;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.statistic.dto.StatisticDTO;
import com.example.moim.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private StatisticService statisticService;

    // 전적 조회
    @GetMapping("/statistic/{clubId}")
    public BaseResponse<StatisticDTO.StatisticResponse> getStatistic(@PathVariable Long clubId,
                                                                     @RequestBody StatisticDTO.StatisticRequest request) {
        return BaseResponse.onSuccess(statisticService.getStatistic(clubId, request.getTargetSeason()), ResponseCode.OK);
    }
}
