package com.example.moim.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MAN, WOMAN;
    
    @JsonCreator
    public static Gender from(String gender){
        return Gender.valueOf(gender.toUpperCase());
    }
}
