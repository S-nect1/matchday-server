package com.example.moim.match.dto;

import com.example.moim.club.entity.Club;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class NearClubsOutput {
    private String title;
    private String image;

    public NearClubsOutput(Club club) {
        this.title = club.getTitle();
        if (club.getImgUrl() != null) {
            try {
                this.image = Base64.getEncoder().encodeToString(new FileUrlResource(club.getImgUrl()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
