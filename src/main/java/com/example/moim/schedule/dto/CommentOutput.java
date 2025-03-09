package com.example.moim.schedule.dto;

import com.example.moim.schedule.entity.Comment;
import lombok.Data;

@Data
public class CommentOutput {
    private Long id;
    private String userName;
    private String contents;

    public CommentOutput(Comment comment) {
        this.id = comment.getId();
        this.userName = comment.getUser().getName();
        this.contents = comment.getContents();
    }
}
