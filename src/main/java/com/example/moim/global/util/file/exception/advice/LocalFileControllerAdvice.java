package com.example.moim.global.util.file.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class LocalFileControllerAdvice extends GeneralException {
    public LocalFileControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
