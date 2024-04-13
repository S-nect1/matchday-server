package com.example.moim.club.dto;

import lombok.Data;

@Data
public class AwardInput {
    private Long id;
    private Long clubId;
    private String title;
    private Integer priority;
    private String imgPath;//앱에 있는 이미지중 어떤거 쓸건지
}
