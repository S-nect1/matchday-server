package com.example.moim.club.dto.request;

import com.example.moim.global.enums.ClubRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubUserUpdateInput {
    /**
     * FIXME: id 라고 하면 무슨 id 인지 모르니까, clubId 로 필드명 변경하기
     */
    private Long id;
    /**
     * FIXME: userId 가 왜 여기있지, 이거 Body 로 전달해도 괜찮은건가..?
     */
    private Long userId;
    private String clubRole;

    @Builder
    public ClubUserUpdateInput(Long id, Long userId, String clubRole) {
        this.id = id;
        this.userId = userId;
        this.clubRole = clubRole;
    }
}
