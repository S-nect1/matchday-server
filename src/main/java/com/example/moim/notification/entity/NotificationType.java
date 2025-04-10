package com.example.moim.notification.entity;

import java.util.IllegalFormatException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    CLUB_JOIN("클럽 가입", "%s님이 %s에 가입했습니다."),
    SCHEDULE_SAVE("일정 등록", "%s 일정이 등록되었습니다. \n 참가 여부를 투표해주세요!!"),
    SCHEDULE_REMINDER("일정 하루 전", "내일 %s 일정이 있습니다."),
    SCHEDULE_ENCOURAGE("투표 독려", "%s 일정이 참가투표가 곧 마감됩니다.\n 참가 여부를 투표해주세요!!"),
    SCHEDULE_JOIN("일정 참여", "%s 일정에 참여했습니다."),
    MATCH_SCHEDULED("매치 등록", "%s 클럽 %s %s 매치가 등록되었습니다.\n 매치정보를 확인하고 신청해주세요!"),
    MATCH_SUCCESS("매칭 성공", "%s 클럽과의 %s %s 매치가 확정되었습니다.\n 매치정보를 다시 한 번 확인해주세요!"),
    MATCH_REVIEW("매치 리뷰", "%s 클럽과의 매치는 즐거우셨나요?\n %s 님의 득점 기록을 입력해주세요!"),
    MATCH_SUGGESTION("매치 건의", "클럽원이 %s 클럽과의 %s %s 매치를 원합니다.\n 매치 정보를 확인하고 신청해주세요!"),
    MATCH_REQUEST("매치 요청", "%s 클럽이 %s 매치에 신청했습니다.\n 클럽 정보를 확인하고 매치를 확정해주세요!"),
    MATCH_INVITE("매치 초대", "%s 클럽에서 친선 매치를 제안했습니다.\n 클럽 정보를 확인하고 매치를 확정해주세요!"),
    MATCH_FAILED_UNREQUESTED("매치 실패", "신청 클럽이 없어 <%s> 매치가 성사되지않았습니다 \uD83D\uDE2D\n 다음에 다시 등록해주세요!"),
    MATCH_FAILED_UNSELECTED("매치 실패", "<%s> 매치 등록 클럽이 다른 클럽을 선택했어요\uD83E\uDEE3\n 다음에 다시 신청해주세요!"),
    MATCH_CANCEL_USER("매치 취소", "<%s> 매치가 취소되었습니다.\n 다음에 다시 신청해주세요!"),
    MATCH_CANCEL_CLUB("매치 취소", "<%s> 매치가 취소되었습니다.\n 다음에 다시 신청해주세요!"),
    ;

    private final String title;
    private final String messageTemplate;

    public String formatMessage(Object... args) throws IllegalFormatException {
        return String.format(messageTemplate, args);
    }

    public String getCategory() {
        return name().substring(0, name().indexOf("_")).toLowerCase();
    }
}
