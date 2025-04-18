package com.example.moim.club.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeInput {
    private String title;
    private String content;

    @Builder
    public NoticeInput(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
