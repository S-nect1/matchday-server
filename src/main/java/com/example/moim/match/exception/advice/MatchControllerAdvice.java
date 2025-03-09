package com.example.moim.match.exception.advice;

import com.example.moim.global.exception.ErrorResult;
import com.example.moim.match.controller.MatchController;
import com.example.moim.match.exception.MatchPermissionException;
import com.example.moim.match.exception.MatchRecordExpireException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MatchController.class)
public class MatchControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> MatchSaveExHandle(MatchPermissionException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> MatchRecordExpireExHandle(MatchRecordExpireException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
