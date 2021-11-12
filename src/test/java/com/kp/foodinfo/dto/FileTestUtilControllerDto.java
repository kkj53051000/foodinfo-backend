package com.kp.foodinfo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;

@Data
@NoArgsConstructor
public class FileTestUtilControllerDto {
    private MockMultipartFile file;
    private MockHttpServletRequest request;
    private String realPath;

    public FileTestUtilControllerDto(MockMultipartFile file, MockHttpServletRequest request, String realPath) {
        this.file = file;
        this.request = request;
        this.realPath = realPath;
    }
}
