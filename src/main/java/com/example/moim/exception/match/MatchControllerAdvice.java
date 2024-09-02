package com.example.moim.exception.match;

import com.example.moim.exception.ErrorResult;
import com.example.moim.match.controller.MatchController;
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
