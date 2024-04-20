package com.example.moim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MoimApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MoimApplication.class, args);
    }
    
}
