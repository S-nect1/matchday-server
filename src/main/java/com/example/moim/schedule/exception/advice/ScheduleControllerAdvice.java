package com.example.moim.schedule.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;
public class ScheduleControllerAdvice extends GeneralException {
    public ScheduleControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> HttpMessageNotReadableExHandle(HttpMessageNotReadableException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> ClubPermissionExHandle(ClubPermissionException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.UNAUTHORIZED);
//    }
}
