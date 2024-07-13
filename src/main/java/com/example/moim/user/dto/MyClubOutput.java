package com.example.moim.user.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class MyClubOutput {
    private Long clubId;
    private String clubName;
    private String explanation;
    private String profileImg;
    private int memberCount;

    public MyClubOutput(Club club) {
        this.clubId = club.getId();
        this.clubName = club.getTitle();
        this.explanation = club.getExplanation();
        try {
            if (club.getProfileImgPath() != null) {
                this.profileImg = Base64.getEncoder().encodeToString(new FileUrlResource(club.getProfileImgPath()).getContentAsByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.memberCount = club.getMemberCount();
    }
}
