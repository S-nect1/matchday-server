package com.example.moim.global.util.file.service;

import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.file.exception.advice.AwsS3ControllerAdvice;
import com.example.moim.global.util.file.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@Disabled("S3 환경에서만 실행 가능. 활성화 시 프로파일 필요")
@SpringBootTest
class CloudFileServiceTest {

    @Autowired
    private ApplicationContext context;

    private AwsS3FileService fileService;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @BeforeEach
    void setUp() {
        fileService = context.getBean(AwsS3FileService.class);
    }

    @Test
    void upload() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName.jpg", "image/png", "ddd".getBytes());

        log.debug("originalName = {}", mockMultipartFile.getOriginalFilename());

        //when
        FileInfo fileInfo = fileService.upload(mockMultipartFile, "test");

        //then
        assertThat(fileInfo.getFileUrl()).contains("/test");
        assertThat(fileService.doesObjectExist("test/aaaa-aaaa-aaaa.jpg")).isTrue();

        fileService.remove(fileInfo.getStoredFileName());
    }

    @Test
    void upload_wrong_file_type() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName", "image/png", "ddd".getBytes());

        log.debug("originalName = {}", mockMultipartFile.getOriginalFilename());

        //when
        //then
        Exception exception = assertThrows(AwsS3ControllerAdvice.class, () -> {
            fileService.upload(mockMultipartFile, "test");
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.FILE_CONTENT_TYPE_NOT_IMAGE.getMessage());
    }

    @Test
    void remove() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("delete file", "delete_originalName.jpg", "image/png", "delete".getBytes());

        FileInfo fileInfo = fileService.upload(mockMultipartFile, "test");

        //when
        fileService.remove(fileInfo.getStoredFileName());

        //then
        assertThat(fileService.doesObjectExist(fileInfo.getStoredFileName())).isFalse();

    }
}