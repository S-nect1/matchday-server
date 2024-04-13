package com.example.moim.club.dto;

import lombok.Data;

@Data
public class ClubUserUpdateInput {
    private Long id;
    private Long userId;
    private String position;
    private String category;

}
