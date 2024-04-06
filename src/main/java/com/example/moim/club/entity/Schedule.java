package com.example.moim.club.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String title;
    private String contents;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int personnel;//참여인원수
}
