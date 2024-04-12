package com.example.moim.club.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClubInput {
    private String title;
    private String explanation;
    private MultipartFile profileImg;
    private MultipartFile backgroundImg;
}
