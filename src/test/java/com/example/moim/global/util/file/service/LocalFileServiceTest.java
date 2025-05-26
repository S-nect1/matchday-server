package com.example.moim.global.util.file.service;

import com.example.moim.global.util.file.model.FileInfo;
import com.example.moim.global.util.uuid.UuidHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class LocalFileServiceTest {

    @Mock
    private UuidHolder testUuidHolder;
    @InjectMocks
    private LocalFileService localFileService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(localFileService, "fileUploadDir", "test_img_dir");
    }

    @Test
    void upload() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName.jpg", "image/png", "ddd".getBytes());

        //when
        when(testUuidHolder.randomUuid()).thenReturn("aaaa-aaaa-aaaa");

        //then
        FileInfo fileInfo = localFileService.upload(mockMultipartFile, "/test");

        log.info("fileUrl : {}", fileInfo.getFileUrl());
        log.info("originalName : {}", fileInfo.getOriginalFileName());
        log.info("storedName : {}", fileInfo.getStoredFileName());

        assertThat(fileInfo.getFileUrl()).contains("/test");
        assertThat(fileInfo.getStoredFileName()).contains("aaaa-aaaa-aaaa");
        assertThat(fileInfo.getOriginalFileName()).contains("originalName.jpg");
        assertThat(new File(fileInfo.getFileUrl()).exists()).isTrue();

        localFileService.remove(fileInfo.getStoredFileName());
    }

    @Test
    void remove() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("delete file", "delete originalName", "image/png", "delete".getBytes());
        when(testUuidHolder.randomUuid()).thenReturn("aaaa-aaaa-aaaa");
        FileInfo fileInfo = localFileService.upload(mockMultipartFile, "/test");

        //when
        localFileService.remove(fileInfo.getStoredFileName());

        //then
        assertThat(new File(fileInfo.getStoredFileName()).exists()).isFalse();
    }
}