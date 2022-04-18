package com.kp.foodinfo.util;

import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.dto.FileTestUtilDto;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTestUtil {
    //  (Service 에서 사용) (realPath, multipartFile)
    public static FileTestUtilDto getTestMultifile() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String realPath = request.getServletContext().getRealPath("");

        String tempPath = "";

        for (int i = 0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

        String publicRealPath = tempPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator;

        Path path = Paths.get(publicRealPath + "test.jpg");

        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {

        }

        MultipartFile multipartFile = new MockMultipartFile("test.jpg", "test.jpg", "multipart/form-data", content);

        FileTestUtilDto fileTestUtilDto = new FileTestUtilDto(realPath, multipartFile);

        return fileTestUtilDto;
    }

    // MockMvc (Controller 에서 사용) (MockMultipartFile, MockHttpServletRequest, realPath)
    public static FileTestUtilControllerDto getTestMultifileController() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        FileTestUtilDto fileTestUtilDto = getTestMultifile();

        MockMultipartFile multipartFile = (MockMultipartFile) fileTestUtilDto.getMultipartFile();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                String.valueOf(MediaType.IMAGE_JPEG),
                multipartFile.getBytes()
        );

        FileTestUtilControllerDto fileRequest = new FileTestUtilControllerDto(file, request, fileTestUtilDto.getRealPath());

        return fileRequest;
    }

    public static MockMultipartFile jsonChangeMockMultipartFile(String json) {
        MockMultipartFile jsonFile = new MockMultipartFile("value", "", "application/json", json.getBytes());

        return jsonFile;
    }
}
