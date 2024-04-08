package com.example.moim.global.entity;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
