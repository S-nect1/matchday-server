package com.example.moim.club.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class ClubSearchOutput {
    private Long id;
    private String title;
    private String explanation;
    private int memberCount;
    private String profileImg;

    public ClubSearchOutput(Club club) {
        this.id = club.getId();
        this.title = club.getTitle();
        this.explanation = club.getExplanation();
        this.memberCount = club.getMemberCount();
        if (club.getProfileImgPath() != null) {
            try {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
