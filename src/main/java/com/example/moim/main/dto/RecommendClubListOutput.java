package com.example.moim.main.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class RecommendClubListOutput {
    private Long id;
    private String title;
    private String explanation;
    private int memberCount;
    private String backgroundImg;

    public RecommendClubListOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.memberCount = club.getMemberCount();
        if (club.getBackgroundImgPath() != null) {
            try {
                this.backgroundImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getBackgroundImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
