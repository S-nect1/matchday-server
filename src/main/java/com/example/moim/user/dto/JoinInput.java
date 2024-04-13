package com.example.moim.user.dto;

import lombok.Data;

@Data
public class JoinInput {
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String gender;
    private String phone;
}