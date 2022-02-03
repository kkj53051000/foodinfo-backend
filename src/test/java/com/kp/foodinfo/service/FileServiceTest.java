//package com.kp.foodinfo.service;
//
//import com.kp.foodinfo.dto.FileTestUtilDto;
//import com.kp.foodinfo.util.FileTestUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.Rollback;
//
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@SpringBootTest
//class FileServiceTest {
//    @Autowired
//    FileService fileService;
//
//    @Test
//    //@Rollback(value = false)
//    void FILE_IMAGE_UPLOAD_PROCESS_TEST() throws IOException {
//        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();
//
//
//        fileService.imageUploadProcess(fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath());
//    }
//}