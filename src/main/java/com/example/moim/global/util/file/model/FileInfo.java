package com.example.moim.global.util.file.model;

import lombok.Builder;
import lombok.Data;

@Data
public class FileInfo {

    private String originalFileName;
    private String storedFileName;
    private String fileUrl;

    @Builder
    public FileInfo(String originalFileName, String storedFileName, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileUrl = fileUrl;
    }
}
