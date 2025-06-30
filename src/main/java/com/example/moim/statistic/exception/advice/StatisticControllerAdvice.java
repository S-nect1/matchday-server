package com.example.moim.statistic.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class StatisticControllerAdvice extends GeneralException {

    public StatisticControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }

}
