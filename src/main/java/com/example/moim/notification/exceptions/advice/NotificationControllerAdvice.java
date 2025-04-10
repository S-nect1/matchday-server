package com.example.moim.notification.exceptions.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class NotificationControllerAdvice extends GeneralException {
    public NotificationControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
