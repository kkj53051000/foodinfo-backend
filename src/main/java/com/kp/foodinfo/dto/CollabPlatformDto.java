package com.kp.foodinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class CollabPlatformDto {
    private String name;
    private MultipartFile file;
    private String realPath;

    public CollabPlatformDto(String name, MultipartFile file, String realPath) {
        this.name = name;
        this.file = file;
        this.realPath = realPath;
    }
}
