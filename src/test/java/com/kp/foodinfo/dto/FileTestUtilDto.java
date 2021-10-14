package com.kp.foodinfo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class FileTestUtilDto {
    private String realPath;
    private MultipartFile multipartFile;

    public FileTestUtilDto(String realPath, MultipartFile multipartFile){
        this.realPath = realPath;
        this.multipartFile = multipartFile;
    }
}
