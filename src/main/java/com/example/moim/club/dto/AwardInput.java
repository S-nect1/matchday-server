package com.example.moim.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AwardInput {
    private Long id;
    private Long clubId;
    @NotBlank(message = "수상 내역 이름을 입력해주세요.")
    private String title;
    @Schema(description = "수상 내역의 중요도. 높을수록 조회시 먼저나옴.")
    private Integer priority;
    @Schema(description = "앱에 있는 이미지중 어떤거 쓸건지 이미지 이름 입력")
    private String imgPath;
}
