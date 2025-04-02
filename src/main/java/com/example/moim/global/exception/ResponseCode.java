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

    // Token Error
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4001", "헤더에 토큰 값이 없습니다"),
    TOKEN_EXPIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4002", "토큰의 유효 기간이 만료되었습니다"),
    TOKEN_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4003", "유효하지 않은 토큰입니다"),
    JWT_SIGNATURE_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "TOKEN4004", "JWT 토큰이 올바르지 않습니다(header.payload.signature)"),

    // AWS S3 Error
    S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "S34001", "파일 업로드에 실패했습니다."),
    S3_PATH_NOT_FOUND(HttpStatus.BAD_REQUEST, "S34002", "파일이 존재하지 않습니다."),

    // Club Error
    CLUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "CLUB4001", "존재하지 않는 모임입니다."),
    CLUB_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "CLUB4002", "가입되지 않은 회원입니다."),
    CLUB_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "CLUB4003", "모임 정보를 수정할 권한이 없습니다."),
    CLUB_PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED, "CLUB4004", "모임 비밀번호가 틀렸습니다."),
    CLUB_CHECK_PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED, "CLUB4005", "모임 확인 비밀번호가 틀렸습니다."),
    CLUB_USER_ALREADY_JOINED(HttpStatus.CONFLICT, "CLUB4006", "해당 사용자는 이미 모임에 가입되어 있습니다."),

    // File Error
    FILE_MAX_SIZE_OVER(HttpStatus.PAYLOAD_TOO_LARGE, "FILE4001", "100MB 이하 파일만 업로드 할 수 있습니다."),
    FILE_CONTENT_TYPE_NOT_IMAGE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "FILE4002", "이미지 파일만 업로드할 수 있습니다."),
    FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FILE4003", "파일 저장에 실패했습니다. 서버에 문의하세요."),

    // Enum Error
    INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST, "ENUM4001", "지원하지 않는 카테고리입니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "ENUM4002", "유효하지 않은 성별입니다."),
    INVALID_CLUB_CATEGORY(HttpStatus.BAD_REQUEST, "ENUM4003", "유효하지 않은 클럽 종류입니다."),
    INVALID_AGE_RANGE(HttpStatus.BAD_REQUEST, "ENUM4004", "유효하지 않은 연령대입니다."),
    INVALID_ACTIVITY_AREA(HttpStatus.BAD_REQUEST, "ENUM4005", "유효하지 않은 지역입니다."),
    INVALID_MAIN_FOOT(HttpStatus.BAD_REQUEST, "ENUM4006", "유효하지 않은 주발입니다."),
    INVALID_SPORTS_TYPE(HttpStatus.BAD_REQUEST, "ENUM4007", "유효하지 않은 종목입니다.");

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
