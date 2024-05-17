package com.example.moim.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MAN, WOMAN;
    
    @JsonCreator
    public static Gender from(String gender){
        if(gender.equals("male")){
            gender = "man";
        } else if(gender.equals("female")){
            gender = "woman";
        }
        return Gender.valueOf(gender.toUpperCase());
    }
}
