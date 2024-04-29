package com.example.moim.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClubInput {
    @NotBlank(message = "모임 이름을 적어야합니다!")
    private String title;
    @NotBlank(message = "모임 설명을 적어야합니다!")
    private String explanation;
    private MultipartFile profileImg;
    private MultipartFile backgroundImg;
}
