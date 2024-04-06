package com.example.moim.club.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Club {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String explanation;
    private String profileImgPath;
    private String backgroundImgPath;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<UserClub> userClub = new ArrayList<>();
}
