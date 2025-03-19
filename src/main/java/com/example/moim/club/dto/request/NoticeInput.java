package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class NoticeInput {
    private Long clubId;
    private String title;
    private String content;

    @Builder
    public NoticeInput(Long clubId, String title, String content) {
        this.clubId = clubId;
        this.title = title;
        this.content = content;
    }
}
