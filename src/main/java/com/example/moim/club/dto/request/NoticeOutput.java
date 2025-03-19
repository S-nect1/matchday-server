package com.example.moim.club.dto.request;

import com.example.moim.club.entity.Notice;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeOutput {
    private Long id;
    private String title;
    private String content;
    private LocalDate createdDate;

    public NoticeOutput(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdDate = notice.getCreatedDate().toLocalDate();
    }
}
