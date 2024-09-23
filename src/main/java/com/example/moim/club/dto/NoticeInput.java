package com.example.moim.club.dto;

import lombok.Data;

@Data
public class NoticeInput {
    private Long clubId;
    private String title;
    private String content;
}
