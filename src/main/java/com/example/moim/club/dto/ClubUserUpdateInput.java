package com.example.moim.club.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClubUserUpdateInput {
    /**
     * FIXME: id 라고 하면 무슨 id 인지 모르니까, clubId 로 필드명 변경하기
     */
    private Long id;
    private Long userId;
    private String position;
    private String category;

    @Builder
    public ClubUserUpdateInput(Long id, Long userId, String position, String category) {
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.category = category;
    }
}
