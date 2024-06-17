package com.example.moim.club.dto;

import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.util.Base64;

@Data
public class ScheduleUserOutput {
    private String name;
    private String img;

    public ScheduleUserOutput(String name, String imgPath) {
        this.name = name;
        if (imgPath != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(imgPath).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
