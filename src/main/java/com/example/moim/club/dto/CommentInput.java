package com.example.moim.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentInput {
    private Long id;
    @NotBlank(message = "댓글을 입력해주세요.")
    private String contents;
}
