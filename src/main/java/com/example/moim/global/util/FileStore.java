package com.example.moim.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public void storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFile(multipartFile);
            }
        }
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null ||multipartFile.isEmpty()) {
            return null;
        }
        String fullPath = getFullPath(UUID.randomUUID() + "." + extractExt(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(new File(fullPath));
        return fullPath;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
