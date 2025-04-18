package com.example.moim.schedule.comment.dto;

import com.example.moim.schedule.comment.entity.Comment;
import lombok.Data;

@Data
public class CommentOutput {
    private Long id;
    private String userName;
    private String contents;
    private String createdDate;

    public CommentOutput(Comment comment) {
        this.id = comment.getId();
        this.userName = comment.getUser().getName();
        this.contents = comment.getContents();
        this.createdDate = comment.getCreatedDate().toString();
    }
}
