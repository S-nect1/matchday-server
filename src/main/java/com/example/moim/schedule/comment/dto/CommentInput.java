package com.example.moim.schedule.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentInput {
    @NotBlank(message = "댓글을 입력해주세요.")
    private String contents;

    @Builder
    public CommentInput(String contents) {
        this.contents = contents;
    }
}
