package com.example.moim.global.util.file.service;

import com.example.moim.global.util.uuid.UuidHolder;
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
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName", "image/png", "ddd".getBytes());

        //when
        when(testUuidHolder.randomUuid()).thenReturn("aaaa-aaaa-aaaa");

        //then
        String filePath = localFileService.upload(mockMultipartFile, "/test");

        assertThat(filePath).contains("/test");
        assertThat(filePath).contains("aaaa-aaaa-aaaa");
        assertThat(filePath).contains("originalName");
        assertThat(new File(filePath).exists()).isTrue();

        localFileService.remove(filePath);
    }

    @Test
    void remove() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("delete file", "delete originalName", "image/png", "delete".getBytes());
        when(testUuidHolder.randomUuid()).thenReturn("aaaa-aaaa-aaaa");
        String filePath = localFileService.upload(mockMultipartFile, "/test");

        //when
        localFileService.remove(filePath);

        //then
        assertThat(new File(filePath).exists()).isFalse();
    }
}