package com.example.moim.schedule.exception.advice;

import com.example.moim.club.exception.ClubPermissionException;
import com.example.moim.schedule.controller.ScheduleController;
import com.example.moim.global.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ScheduleController.class)
public class ScheduleControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> HttpMessageNotReadableExHandle(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> ClubPermissionExHandle(ClubPermissionException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
