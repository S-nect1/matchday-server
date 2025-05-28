package com.example.moim.global.util.file.service;

import com.example.moim.global.util.file.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileInfo upload(MultipartFile multipartFile, String directoryName) throws IOException;
    void remove(String path);
}
