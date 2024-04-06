package com.example.moim.club.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;

@Data
public class ClubOutput {
    private Long id;
    private String title;
    private String explanation;
    private String profileImg;
    private String backgroundImg;

    public ClubOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.profileImg = club.getProfileImgPath();//base64 인코딩
        this.backgroundImg = club.getBackgroundImgPath();
    }
}
