package com.example.moim.club.dto;

import com.example.moim.club.entity.Award;
import lombok.Data;

@Data
public class AwardOutput {
    private Long id;
    private String title;
    private String imgPath;

    public AwardOutput(Award award) {
        this.id = award.getId();
        this.title = award.getTitle();
        this.imgPath = award.getImgPath();
    }
}
