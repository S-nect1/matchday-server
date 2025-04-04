package com.example.moim.global.util.file.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Slf4j
@Profile("test")
@Configuration
public class LocalFileConfig {

    @Value("${local.upload.directory}")
    private String fileUploadDir;

//    @Bean
//    public File file() {
//
//        File directory = new File(fileUploadDir);
//
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//
//        return directory;
//    }
}
