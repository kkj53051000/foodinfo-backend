package com.kp.foodinfo.util;

import com.kp.foodinfo.dto.FileTestUtilDto;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTestUtil {
    public static FileTestUtilDto getTestMultifile() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String realPath = request.getServletContext().getRealPath("");

        String tempPath = "";

        for(int i=0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

        String publicRealPath = tempPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator;

        Path path = Paths.get(publicRealPath + "test.jpg");

        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e){

        }

        MultipartFile multipartFile = new MockMultipartFile("test.jpg", "test.jpg", "multipart/form-data", content);

        FileTestUtilDto fileTestUtilDto = new FileTestUtilDto(realPath, multipartFile);

        return fileTestUtilDto;
    }
    public static MultipartFile abc2(MockHttpServletRequest request, String realPath) {
        String tempPath = "";

        for(int i=0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

        String publicRealPath = tempPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator;

        Path path = Paths.get(publicRealPath + "test.jpg");

        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e){

        }

        MultipartFile multipartFile = new MockMultipartFile("test.jpg", "test.jpg", "multipart/form-data", content);

        return multipartFile;
    }
}
