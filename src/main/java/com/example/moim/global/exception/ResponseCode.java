package com.example.moim.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 정상 code
    OK(HttpStatus.OK,"2000", "Ok"),

    // Common Error
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON001","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON002","권한이 잘못되었습니다"),
    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    // Member Error
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    // Review Error
    REVIEW_ALREADY_FOUND(HttpStatus.BAD_REQUEST, "REVIEW4001", "지정된 리뷰의 개수를 초과했습니다."),

    // Club Error
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB4001", "클럽을 찾을 수 없습니다."),

    // Match Error
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "MATCH4001", "매치를 찾을 수 없습니다."),
    REQUIRE_FEE(HttpStatus.BAD_REQUEST, "MATCH4002", "대관비를 입력해주세요."),
    REQUIRE_ACCOUNT(HttpStatus.BAD_REQUEST, "MATCH4003", "계좌번호를 입력해주세요."),
    MATCH_ALREADY_FOUND(HttpStatus.BAD_REQUEST, "MATCH4004", "이미 신청한 매치입니다."),
    REGISTERED_CLUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "MATCH4005", "가입된 동아리가 없습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MATCH4006", "올바른 매치가 아닙니다."),
    TIME_OUT(HttpStatus.BAD_REQUEST, "MATCH4007", "매치가 종료된 후 48시간이 지났습니다."),

    // Token Error
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4001", "헤더에 토큰 값이 없습니다"),
    TOKEN_EXPIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4002", "토큰의 유효 기간이 만료되었습니다"),
    TOKEN_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4003", "유효하지 않은 토큰입니다"),
    JWT_SIGNATURE_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4004", "JWT 토큰이 올바르지 않습니다(header.payload.signature)"),

    // AWS S3 Error
    S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "S34001", "파일 업로드에 실패했습니다."),
    S3_PATH_NOT_FOUND(HttpStatus.BAD_REQUEST, "S34002", "파일이 존재하지 않습니다."),

    // Store Error
    SEARCH_KEYWORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE4001", "입력된 검색어가 없습니다."),
    PAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE4002", "없는 페이지입니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE4003", "존재하지 않는 상점입니다."),
    ALREADY_HEART_EXCEPTION(HttpStatus.BAD_REQUEST, "STORE4004", "이미 관심 상점으로 등록되었습니다."),
    NOT_HEART_EXCEPTION(HttpStatus.BAD_REQUEST, "STORE4005", "해당 상점은 관심 상점으로 등록되지 않았으므로 삭제할 수 없습니다."),
    PAGE_AND_SIZE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "STORE4006", "page 와 size 는 0 과 양수만 입력 가능합니다."),

    // File Error
    FILE_MAX_SIZE_OVER(HttpStatus.PAYLOAD_TOO_LARGE, "FILE4001", "100MB 이하 파일만 업로드 할 수 있습니다."),
    FILE_CONTENT_TYPE_NOT_IMAGE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "FILE4002", "이미지 파일만 업로드할 수 있습니다."),
    FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FILE4003", "파일 저장에 실패했습니다. 서버에 문의하세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ResponseCode valueOf(HttpStatus httpStatus) {
        if(httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if(httpStatus.is4xxClientError()) {
                        return ResponseCode._BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return ResponseCode._INTERNAL_SERVER_ERROR;
                    } else {
                        return ResponseCode.OK;
                    }
                });
    }
}
