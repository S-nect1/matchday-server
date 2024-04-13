package com.example.moim.club.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClubImgInput {
    private Long id;
    private MultipartFile img;
}
